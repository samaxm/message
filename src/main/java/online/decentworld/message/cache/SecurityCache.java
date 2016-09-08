package online.decentworld.message.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * 驗證碼相關緩存
 * @author Sammax
 *
 */
@Component
public class SecurityCache {
	
	private static Logger logger=LoggerFactory.getLogger(SecurityCache.class);
	
	@Autowired
	private RedisTemplate template;


	public void cacheAES(String dwID,String aes) throws Exception {
		ReturnResult result=template.cache((Jedis jedis)->{
			jedis=RedisClient.getJedis();
			jedis.hset(MessageCacheKey.AES,dwID,aes);
			return ReturnResult.SUCCESS;
		});
		if(!result.isSuccess()){
			throw new Exception();
		}
	}

	public String getAES(String dwID){
		ReturnResult result=template.cache((Jedis jedis)->{
			jedis=RedisClient.getJedis();
			String key=jedis.hget(MessageCacheKey.AES,dwID);
			return ReturnResult.result(key);
		});
		if(result.isSuccess()){
			return (String)result.getResult();
		}else{
			return null;
		}
	}

}
