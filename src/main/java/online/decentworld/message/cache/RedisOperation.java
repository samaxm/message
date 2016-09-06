package online.decentworld.message.cache;

import redis.clients.jedis.Jedis;

public interface RedisOperation {

	
	public ReturnResult execute(Jedis jedis);
}
