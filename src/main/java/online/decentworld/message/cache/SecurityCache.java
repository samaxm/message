package online.decentworld.message.cache;

import online.decentworld.cache.redis.CacheKey;
import online.decentworld.cache.redis.RedisClient;
import online.decentworld.cache.redis.RedisTemplate;
import online.decentworld.cache.redis.ReturnResult;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Created by Sammax on 2016/9/12.
 */
@Component
public class SecurityCache extends RedisTemplate {

    public void cacheAES(String dwID,String aes) throws Exception {
        ReturnResult result=cache((Jedis jedis)->{
            jedis= RedisClient.getJedis();
            jedis.hset(CacheKey.AES,dwID,aes);
            return ReturnResult.SUCCESS;
        });
        if(!result.isSuccess()){
            throw new Exception();
        }
    }

    public String getAES(String dwID){
        ReturnResult result=cache((Jedis jedis)->{
            jedis=RedisClient.getJedis();
            String key=jedis.hget(CacheKey.AES,dwID);
            return ReturnResult.result(key);
        });
        if(result.isSuccess()){
            return (String)result.getResult();
        }else{
            return null;
        }
    }

}
