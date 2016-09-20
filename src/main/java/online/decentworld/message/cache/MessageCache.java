package online.decentworld.message.cache;

import online.decentworld.cache.redis.*;
import online.decentworld.message.common.MessageCacheConfig;
import online.decentworld.message.common.MessageProcessStatus;
import online.decentworld.message.exception.PersistMessageFailException;
import online.decentworld.rpc.codc.Codec;
import online.decentworld.rpc.dto.message.ChatMessage;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.WealthAckMessage;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sammax on 2016/9/12.
 */
@Component
public class MessageCache extends RedisTemplate {

    @Autowired
    private RedisIDUtil idUtil;
    @Resource(name = "protosCodec")
    private Codec codec;

    private static Logger logger= LoggerFactory.getLogger(MessageCache.class);


    public long cacheMessage(MessageWrapper msg,WealthAckMessage ack) throws PersistMessageFailException {
        ReturnResult result= cache((Jedis jedis)->{
            long id=idUtil.getID(jedis);
            String senderID=msg.getSender();
            String receiverID=msg.getReceiver();
            jedis.zadd(MessageCacheKey.getUserMessageCacheKey(senderID), id, MessageProcessStatus.S.name()+id);
            //cache temp wealth ack for recheck
            byte[] wealth_ack=codec.encode(new MessageWrapper("SYSTEM_MESSAGE_SENDER", msg.getReceiver(), MessageType.WEALTH_ACK, ack));
            jedis.setex(((ChatMessage) msg.getBody()).getTempID().getBytes(), MessageCacheConfig.WEALTH_ACK_SECONDS,wealth_ack);
            jedis.zadd(MessageCacheKey.getUserMessageCacheKey(receiverID), id, MessageProcessStatus.R.name()+id);
            jedis.hset(CacheKey.CHAT.getBytes(),String.valueOf(id).getBytes(),codec.encode(msg));
            return ReturnResult.result(id);
        });
        if(!result.isSuccess()){
            logger.warn("[ID_CACHE_FAILED] sender#"+msg.getSender()+" recevier#"+msg.getReceiver()+" data#");
            throw new PersistMessageFailException();
        }else{
            return (long)result.getResult();
        }
    }

    public MessageSynchronizeResult synchronizeMessage(String dwID,long synchronizeNum){
        ReturnResult result=cache((Jedis jedis) -> {
            Set<String> newMessageIDs = jedis.zrangeByScore(MessageCacheKey.getUserMessageCacheKey(dwID), synchronizeNum+1, Integer.MAX_VALUE);
            List<byte[]> msgs=null;
            List<byte[]> notices=null;
            if(newMessageIDs.size()>1){
                msgs= jedis.hmget(MessageCacheKey.CHAT.getBytes(), format(newMessageIDs));
            }else if(newMessageIDs.size()==1){
                byte[] msg= jedis.hget(MessageCacheKey.CHAT.getBytes(),newMessageIDs.iterator().next().getBytes());
                msgs=new ArrayList<byte[]>(1);
                msgs.add(msg);
            }
            notices = jedis.lrange(MessageCacheKey.getUserConsumableCacheKey(dwID).getBytes(), 0, -1);
            MessageSynchronizeResult newMessages = new MessageSynchronizeResult(msgs, notices);
            return ReturnResult.result(newMessages);
        });
        if(result.isSuccess()){
            return (MessageSynchronizeResult) result.getResult();
        }
        return null;
    }

    private byte[][] format(Set<String> ids){
        Set<String> received=new HashSet<>();
        for(String id:ids){
            if(id.startsWith(MessageProcessStatus.R.name())){
                received.add(id.substring(1));
            }
        }
        String[] idArr=received.toArray(new String[received.size()]);
        byte[][] bytes=new byte[idArr.length][];
        for(int i=0;i<idArr.length;i++){
            bytes[i]=idArr[i].getBytes();
        }
        return bytes;
    }

    public static void main(String[] args) {
        Jedis jedis= RedisClient.getJedis();
        List<byte[]> data =jedis.hmget(MessageCacheKey.CHAT.getBytes(), "1".getBytes(), "2".getBytes());
        byte[][] fields=new byte[2][];
        for(int i=0;i<fields.length;i++){
            fields[i]=String.valueOf(i+1).getBytes();
        }
        List<byte[]> data2 =jedis.hmget(MessageCacheKey.CHAT.getBytes(), fields);

    }
}
