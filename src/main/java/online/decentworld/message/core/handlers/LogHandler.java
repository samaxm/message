package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.cache.LocalUserContactCache;
import online.decentworld.message.core.MessageReceiveEvent;
import online.decentworld.rpc.dto.message.ChatMessage;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Sammax on 2016/9/7.
 */

public class LogHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent> {

    private static Logger logger= LoggerFactory.getLogger(LogHandler.class);

    private LocalUserContactCache userContactCache;

    public LogHandler(LocalUserContactCache userContactCache) {
        this.userContactCache = userContactCache;
    }

    public LogHandler() {

    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {
        logger.debug("[CHECKING_CONTACTS]");
        if(messageReceiveEvent.getMsg().getType()== MessageType.CHAT&&messageReceiveEvent.getStatus().isCanDeliver()){
            ChatMessage cm=(ChatMessage)messageReceiveEvent.getMsg().getBody();
            userContactCache.checkContacts(cm.getToID(), cm.getFromID(), cm.getRelation());
        }
    }

    public LocalUserContactCache getUserContactCache() {
        return userContactCache;
    }

    public void setUserContactCache(LocalUserContactCache userContactCache) {
        this.userContactCache = userContactCache;
    }
}
