package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import online.decentworld.message.common.MessageConfig;
import online.decentworld.message.core.*;
import online.decentworld.message.http.RequestHolder;
import online.decentworld.rpc.codc.Codec;
import online.decentworld.rpc.dto.message.ChatMessage;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.WealthAckMessage;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Sammax on 2016/9/7.
 */
public class DispatcherHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent> {

    private static Logger logger= LoggerFactory.getLogger(DispatcherHandler.class);

    private Disruptor<MessageSendEvent> disruptor;
    private SendMessageEventTranslator translator;
    private Codec codec;

    public DispatcherHandler(Disruptor<MessageSendEvent> disruptor, SendMessageEventTranslator translator, Codec codec) {
        this.disruptor = disruptor;
        this.translator = translator;
        this.codec = codec;
    }

    public DispatcherHandler() {
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {
        logger.debug("[DISPATCHER_MSG]");
        MessageStatus status=messageReceiveEvent.getStatus();
        if(status.isValidate()){
            long mid=messageReceiveEvent.getMsg().getMid();
            String sender=messageReceiveEvent.getMsg().getSenderID();
            String receiver=messageReceiveEvent.getMsg().getReceiverID();
            MessageType type=messageReceiveEvent.getMsg().getType();
            if(messageReceiveEvent.getMsg().getType()== MessageType.CHAT_AUDIO||
                    messageReceiveEvent.getMsg().getType()== MessageType.CHAT_IMAGE||
                    messageReceiveEvent.getMsg().getType()== MessageType.CHAT_TEXT){
                ChatMessage cm=(ChatMessage)messageReceiveEvent.getMsg().getBody();
                if(status.isPersistSuccessful()){
                    MessageWrapper msg=messageReceiveEvent.getMsg();
                    WealthAckMessage ackMessage=messageReceiveEvent.getWealthAckMessage();
                    if(ackMessage==null||ackMessage.isChargeSuccess()){
                        MessageSendEventTranslateInfo info=new MessageSendEventTranslateInfo(codec.encode(msg),receiver,type,mid);
                        disruptor.publishEvent(translator,info);
                    }
                    if(ackMessage!=null){
                        MessageSendEventTranslateInfo ack=new MessageSendEventTranslateInfo(codec.encode(new MessageWrapper(MessageConfig.SYSTEM_MESSAGE_SENDER,sender,MessageType.COMMAND_WEALTH_ACK,ackMessage,new Date(),mid)),
                                RequestHolder.getResponseKey(cm.getFromID(), cm.getTempID()),MessageType.COMMAND_WEALTH_ACK,mid);
                        disruptor.publishEvent(translator,ack);
                    }
                }
            }else{
                MessageSendEventTranslateInfo info=new MessageSendEventTranslateInfo(codec.encode(messageReceiveEvent.getMsg()),receiver,type,mid);
                disruptor.publishEvent(translator,info);
            }
        }

    }

    public static DispatcherHandler[] createGroup(int size,Disruptor<MessageSendEvent> disruptor,SendMessageEventTranslator translator,Codec codec){
        DispatcherHandler[] group=new DispatcherHandler[size];
        for(int i=0;i<size;i++){
            group[i]=new DispatcherHandler(disruptor,translator,codec);
        }
        return group;
    }
}
