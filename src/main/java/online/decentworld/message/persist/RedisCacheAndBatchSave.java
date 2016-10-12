package online.decentworld.message.persist;

import online.decentworld.message.cache.MessageCache;
import online.decentworld.message.exception.PersistMessageFailException;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.WealthAckMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/9/14.
 */
@Component
public class RedisCacheAndBatchSave implements PersistStrategy {

    @Autowired
    private MessageCache messageCache;

    @Override
    public MessageWrapper persistMessage(MessageWrapper msg, WealthAckMessage wealthAckMessage) throws PersistMessageFailException {
        return messageCache.cacheMessage(msg,wealthAckMessage);
    }
}
