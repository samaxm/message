package online.decentworld.message.persist;

import online.decentworld.message.exception.PersistMessageFailException;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.WealthAckMessage;

/**
 * Created by Sammax on 2016/9/14.
 */
public interface PersistStrategy {
    void persistMessage(MessageWrapper msg,WealthAckMessage wealthAckMessage) throws PersistMessageFailException;
}
