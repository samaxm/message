package online.decentworld.message.cache;

import online.decentworld.cache.redis.*;
import online.decentworld.message.common.MessageCacheConfig;
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
import java.util.Date;
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


    public MessageWrapper cacheMessage(MessageWrapper msg,WealthAckMessage ack) throws PersistMessageFailException {
        ReturnResult result= cache((Jedis jedis)->{
            long id=idUtil.getID(jedis);
            Date time=new Date();
            if(msg!=null){
                String receiverID=msg.getReceiver();
                msg.setMid(id);
                msg.setTime(time);
                jedis.zadd(MessageCacheKey.getUserMessageCacheKey(receiverID), id, String.valueOf(id));
                jedis.hset(CacheKey.MESSAGE.getBytes(),String.valueOf(id).getBytes(),codec.encode(msg));
            }
            if(ack!=null) {
                //cache temp wealth ack for recheck
                ack.setMid(id);
                byte[] wealth_ack = codec.encode(new MessageWrapper("SYSTEM_MESSAGE_SENDER", msg.getReceiver(), MessageType.WEALTH_ACK, ack,time,id));
                jedis.setex(((ChatMessage) msg.getBody()).getTempID().getBytes(), MessageCacheConfig.WEALTH_ACK_SECONDS, wealth_ack);
            }
            return ReturnResult.result(msg);
        });
        if(!result.isSuccess()){
            logger.warn("[ID_CACHE_FAILED] sender#"+msg.getSender()+" recevier#"+msg.getReceiver()+" data#");
            throw new PersistMessageFailException();
        }else{
            return (MessageWrapper)result.getResult();
        }
    }
    //TODO:lua script to improve performance
    public MessageSynchronizeResult synchronizeMessage(String dwID,long synchronizeNum){
        ReturnResult result=cache((Jedis jedis) -> {
            //get unread messageIDs
            Set<String> newMessageIDs = jedis.zrangeByScore(MessageCacheKey.getUserMessageCacheKey(dwID), synchronizeNum+1, Integer.MAX_VALUE);
            //remove readed messageIDs and store to set waiting for db save task
            Set<String> readedMessageIDs=jedis.zrangeByScore(MessageCacheKey.getUserMessageCacheKey(dwID), 0, synchronizeNum);
            if(readedMessageIDs.size()!=0) {
                String[] readedIds = readedMessageIDs.toArray(new String[readedMessageIDs.size()]);
                jedis.zrem(MessageCacheKey.getUserMessageCacheKey(dwID), readedIds);
                jedis.sadd(MessageCacheKey.MESSSAGE_STORE_SET, readedIds);
            }
            List<byte[]> msgs=null;
            msgs=getFromHSETBytes(MessageCacheKey.MESSAGE,newMessageIDs,jedis);
            MessageSynchronizeResult newMessages = new MessageSynchronizeResult(msgs);
            return ReturnResult.result(newMessages);
        });
        if(result.isSuccess()){
            return (MessageSynchronizeResult) result.getResult();
        }
        return null;
    }

    public static void main(String[] args) {
        Jedis jedis= RedisClient.getJedis();
        List<byte[]> data =jedis.hmget(MessageCacheKey.MESSAGE.getBytes(), "1".getBytes(), "2".getBytes());
        byte[][] fields=new byte[2][];
        for(int i=0;i<fields.length;i++){
            fields[i]=String.valueOf(i+1).getBytes();
        }
        List<byte[]> data2 =jedis.hmget(MessageCacheKey.MESSAGE.getBytes(), fields);

    }
}
