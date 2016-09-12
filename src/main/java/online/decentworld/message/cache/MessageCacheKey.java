package online.decentworld.message.cache;

import online.decentworld.cache.redis.CacheKey;

/**
 * cache management
 * @author Sammax
 *
 */
public class MessageCacheKey extends CacheKey{

    private static String CHAT_CACHE_PREFIX="chat";

    public static String getUserMessageCacheKey(String dwID){
        return (CHAT_CACHE_PREFIX+SEPARATOR+dwID);
    }

}
