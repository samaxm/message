package online.decentworld.message.cache;

import online.decentworld.cache.redis.CacheKey;
import online.decentworld.cache.redis.RedisIDUtil;
import online.decentworld.cache.redis.RedisTemplate;
import online.decentworld.cache.redis.ReturnResult;
import online.decentworld.rpc.dto.message.BaseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Created by Sammax on 2016/9/12.
 */
@Component
public class MessageCache extends RedisTemplate {

    @Autowired
    private RedisIDUtil idUtil;
    private static Logger logger= LoggerFactory.getLogger(MessageCache.class);


    public void cacheMessage(BaseMessage msg){
        ReturnResult result= cache((Jedis jedis)->{
            long id=idUtil.getID(jedis);
            String senderID=msg.getSender().getID();
            String recevierID=msg.getReceiver().getID();
            jedis.zadd(MessageCacheKey.getUserMessageCacheKey(senderID),id,String.valueOf(id));
            jedis.zadd(MessageCacheKey.getUserMessageCacheKey(recevierID),id,String.valueOf(id));
            jedis.hset(CacheKey.CHAT.getBytes(),String.valueOf(id).getBytes(),msg.getWriteByte());
            return ReturnResult.SUCCESS;
        });
        if(!result.isSuccess()){
            logger.warn("[ID_CACHE_FAILED] sender#"+msg.getSender().getID()+" recevier#"+msg.getReceiver().getID()+" data#");
        }
    }


}
