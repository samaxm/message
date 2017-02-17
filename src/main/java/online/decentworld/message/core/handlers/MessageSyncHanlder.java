package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.cache.MessageCache;
import online.decentworld.message.cache.MessageSynchronizeResult;
import online.decentworld.message.core.MessageResendScanner;
import online.decentworld.message.core.MessageSendTimeNode;
import online.decentworld.message.core.channel.LocalNettyChannel;
import online.decentworld.message.core.event.MessageSyncEvent;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.types.MessageType;

/**
 * Created by Sammax on 2016/10/25.
 */

public class MessageSyncHanlder implements EventHandler<MessageSyncEvent>,WorkHandler<MessageSyncEvent> {


    public MessageCache getMessageCache() {
        return messageCache;
    }

    public MessageResendScanner getScanner() {
        return scanner;
    }

    private MessageCache messageCache;
    private MessageResendScanner scanner;

    @Override
    public void onEvent(MessageSyncEvent messageSyncEvent, long l, boolean b) throws Exception {
        onEvent(messageSyncEvent);
    }

    @Override
    public void onEvent(MessageSyncEvent messageSyncEvent) throws Exception {
        MessageSynchronizeResult data=messageCache.synchronizeMessage(messageSyncEvent.getDwID(),messageSyncEvent.getSyncNum());
        if(data.getMessages().size()!=0){
            LocalNettyChannel channel=(LocalNettyChannel)messageSyncEvent.getSession().getMessageChannel();
            channel.batchWriteRaw(data.getMessages());
            scanner.addMessageSyncNode(new MessageSendTimeNode(System.currentTimeMillis(),messageSyncEvent.getSyncNum(),messageSyncEvent.getSession()));
        }else{
            messageSyncEvent.getSession().getMessageChannel().write(MessageWrapper.createCommand(MessageType.COMMAND_MSG_SYNC_FIN));
        }
    }

    public void setMessageCache(MessageCache messageCache) {
        this.messageCache = messageCache;
    }

    public void setScanner(MessageResendScanner scanner) {
        this.scanner = scanner;
    }

    public MessageSyncHanlder(MessageCache messageCache, MessageResendScanner scanner) {
        this.messageCache = messageCache;
        this.scanner = scanner;
    }
    public MessageSyncHanlder() {

    }

    public static MessageSyncHanlder[] createGroup(int size,MessageCache messageCache,MessageResendScanner messageResendScanner){
        MessageSyncHanlder[] group=new MessageSyncHanlder[size];
        for(int i=0;i<size;i++){
            MessageSyncHanlder hanlder =new MessageSyncHanlder();
            hanlder.setMessageCache(messageCache);
            hanlder.setScanner(messageResendScanner);
            group[i]=hanlder;
        }
        return group;
    }
}
