package online.decentworld.message.cache;

import online.decentworld.cache.redis.CacheKey;
import online.decentworld.cache.redis.RedisIDUtil;
import online.decentworld.cache.redis.RedisTemplate;
import online.decentworld.cache.redis.ReturnResult;
import online.decentworld.message.common.MessageCacheConfig;
import online.decentworld.message.common.MessageStatus;
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


    public void cacheMessage(MessageWrapper msg,WealthAckMessage ack) throws PersistMessageFailException {
        ReturnResult result= cache((Jedis jedis)->{
            long id=idUtil.getID(jedis);
            String senderID=msg.getSender();
            String receiverID=msg.getReceiver();
            jedis.zadd(MessageCacheKey.getUserMessageCacheKey(senderID), id, MessageStatus.SENDED.name());
            //cache temp wealth ack for recheck
            byte[] wealth_ack=codec.encode(new MessageWrapper("SYSTEM_MESSAGE_SENDER", msg.getReceiver(), MessageType.WEALTH_ACK, ack));
            jedis.setex(((ChatMessage) msg.getBody()).getTempID().getBytes(), MessageCacheConfig.WEALTH_ACK_SECONDS,wealth_ack);
            jedis.zadd(MessageCacheKey.getUserMessageCacheKey(receiverID), id,MessageStatus.RECEIVED.name());
            jedis.hset(CacheKey.CHAT.getBytes(),String.valueOf(id).getBytes(),codec.encode(msg));
            return ReturnResult.SUCCESS;
        });
        if(!result.isSuccess()){
            logger.warn("[ID_CACHE_FAILED] sender#"+msg.getSender()+" recevier#"+msg.getReceiver()+" data#");
            throw new PersistMessageFailException();
        }
    }
}
