package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.charge.ChargeResultCode;
import online.decentworld.charge.receipt.MessageReceipt;
import online.decentworld.message.common.MessageConfig;
import online.decentworld.message.core.MessageStatus;
import online.decentworld.message.core.event.MessageReceiveEvent;
import online.decentworld.message.core.session.Session;
import online.decentworld.message.core.session.SessionManager;
import online.decentworld.rpc.dto.message.ChatMessage;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.WealthAckMessage;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/10/24.
 */
public class MessageDispatchHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent> {

    private static Logger logger= LoggerFactory.getLogger(DispatcherHandler.class);
    private SessionManager sessionManager;
    public MessageDispatchHandler() {
    }

    public MessageDispatchHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
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
            if(MessageType.isChatMessage(messageReceiveEvent.getMsg().getType())){
                ChatMessage cm=(ChatMessage)messageReceiveEvent.getMsg().getBody();
                 MessageReceipt receipt=messageReceiveEvent.getMessageReceipt();
                if(status.isPersistSuccessful()){
                    WealthAckMessage ackMessage=messageReceiveEvent.getWealthAckMessage();
                    if(ackMessage.isChargeSuccess()){
                        Session receiverSession=sessionManager.getSession(receiver);
                        if(receiverSession!=null){
                            receiverSession.getMessageChannel().write(messageReceiveEvent.getMsg());
                        }
                    }
                    MessageWrapper ack=MessageWrapper.createMessageWrapper(MessageConfig.SYSTEM_MESSAGE_SENDER,cm.getFromID(),
                            new WealthAckMessage(cm.getTempID(),mid,receipt.getChargeResult().getPayerWealth(),
                                    receipt.getChargeResult().getStatusCode()== ChargeResultCode.SUCCESS?true:false,receipt.getChatRelation(),receipt.getChatStatus()),mid);
                    Session senderSession=sessionManager.getSession(sender);
                    if(senderSession!=null){
                        senderSession.getMessageChannel().write(ack);
                    }
                }
            }else{
                Session receiverSession=sessionManager.getSession(receiver);
                if(receiverSession!=null){
                    receiverSession.getMessageChannel().write(messageReceiveEvent.getMsg());
                }
            }
        }

    }

    public static MessageDispatchHandler[] createGroup(int size,SessionManager sessionManager){
        MessageDispatchHandler[] group=new MessageDispatchHandler[size];
        for(int i=0;i<size;i++){
            group[i]=new MessageDispatchHandler(sessionManager);
        }
        return group;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}