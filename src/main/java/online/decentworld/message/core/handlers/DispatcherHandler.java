package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import online.decentworld.message.common.MessageConfig;
import online.decentworld.message.core.*;
import online.decentworld.message.http.ContextHolder;
import online.decentworld.rpc.codc.Codec;
import online.decentworld.rpc.dto.message.ChatMessage;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/9/7.
 */
public class DispatcherHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent> {

    private static Logger logger= LoggerFactory.getLogger(DispatcherHandler.class);

    private Disruptor<MessageSendEvent> disruptor;
    private SendMessageEventTranslator translator;
    private Codec codec;


    public DispatcherHandler(Disruptor<MessageSendEvent> disruptor) {
        this.disruptor = disruptor;
    }
    public DispatcherHandler() {
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {
        logger.debug("[DELIVERING_MSG]");
        MessageStatus status=messageReceiveEvent.getStatus();
        if(messageReceiveEvent.getMsg().getType()== MessageType.CHAT){
            ChatMessage cm=(ChatMessage)messageReceiveEvent.getMsg().getBody();
            if(status.isValidate()&&status.isCanDeliver()&&status.isPersistSuccessful()){
                MessageSendEventTranslateInfo info=new MessageSendEventTranslateInfo(codec.encode(messageReceiveEvent.getMsg()),messageReceiveEvent.getMsg().getReceiver(),messageReceiveEvent.getMsg().getType());
                disruptor.publishEvent(translator,info);
                MessageSendEventTranslateInfo ack=new MessageSendEventTranslateInfo(codec.encode(new MessageWrapper(MessageConfig.SYSTEM_MESSAGE_SENDER,messageReceiveEvent.getMsg().getSender(),MessageType.WEALTH_ACK,messageReceiveEvent.getWealthAckMessage())),
                        ContextHolder.getResponseKey(cm.getFromID(),cm.getTempID()),MessageType.WEALTH_ACK);
                disruptor.publishEvent(translator,ack);
            }
        }else{
            if(status.isValidate()&&status.isCanDeliver()){
                MessageSendEventTranslateInfo info=new MessageSendEventTranslateInfo(codec.encode(messageReceiveEvent.getMsg()),messageReceiveEvent.getMsg().getReceiver(),messageReceiveEvent.getMsg().getType());
                disruptor.publishEvent(translator,info);
            }
        }
    }

    public static DispatcherHandler[] createGroup(int size,Disruptor<MessageSendEvent> disruptor){
        DispatcherHandler[] group=new DispatcherHandler[size];
        for(int i=0;i<size;i++){
            group[i]=new DispatcherHandler(disruptor);
        }
        return group;
    }
}
