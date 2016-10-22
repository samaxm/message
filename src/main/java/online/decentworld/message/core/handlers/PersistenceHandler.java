package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.charge.ChargeResultCode;
import online.decentworld.charge.receipt.MessageReceipt;
import online.decentworld.message.core.MessageReceiveEvent;
import online.decentworld.message.core.MessageStatus;
import online.decentworld.message.persist.PersistStrategy;
import online.decentworld.rpc.dto.message.ChatMessage;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.WealthAckMessage;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/9/7.
 */
public class PersistenceHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent>  {

    private static Logger logger= LoggerFactory.getLogger(PersistenceHandler.class);

    private PersistStrategy persistStrategy;

    public PersistenceHandler(PersistStrategy persistStrategy) {
        this.persistStrategy = persistStrategy;
    }

    public PersistenceHandler() {
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {
        logger.debug("[SAVING TO DB---->]");
        MessageStatus status=messageReceiveEvent.getStatus();
        //only persist validate chat msg
        if(status.isValidate()){
            try{
                WealthAckMessage ackMessage=null;
                if(messageReceiveEvent.getMsg().getType()== MessageType.CHAT_AUDIO||
                        messageReceiveEvent.getMsg().getType()== MessageType.CHAT_IMAGE||
                        messageReceiveEvent.getMsg().getType()== MessageType.CHAT_TEXT) {
                    //chat message should save ack
                    ChatMessage msg = (ChatMessage) messageReceiveEvent.getMsg().getBody();
                    MessageReceipt receipt = messageReceiveEvent.getMessageReceipt();
                    ackMessage = new WealthAckMessage(msg.getTempID(), 0, receipt.getChargeResult().getPayerWealth(), receipt.getChargeResult().getStatusCode() == ChargeResultCode.SUCCESS ? true : false, receipt.getChatRelation(), receipt.getChatStatus());
                    messageReceiveEvent.setWealthAckMessage(ackMessage);
                }
                MessageWrapper msg = persistStrategy.persistMessage(messageReceiveEvent.getMsg(), ackMessage);
                status.setPersistSuccessful(true);
            }catch (Exception e){
                //refund money
                logger.warn("[CACHE_FAILED]",e);
                status.setPersistSuccessful(false);
            }
        }
    }

    public static PersistenceHandler[] createGroup(int size,PersistStrategy strategy){
        PersistenceHandler[] group=new PersistenceHandler[size];
        for(int i=0;i<size;i++){
            group[i]=new PersistenceHandler(strategy);
        }
        return group;
    }

}
