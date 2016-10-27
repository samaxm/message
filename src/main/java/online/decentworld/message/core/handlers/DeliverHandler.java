package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.event.MessageSendEvent;
import online.decentworld.message.http.RequestHolder;
import online.decentworld.message.http.SendMessageRequest;
import online.decentworld.message.http.SynchronizeRequest;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/9/19.
 */
public class DeliverHandler implements EventHandler<MessageSendEvent>,WorkHandler<MessageSendEvent>{

    private  static Logger logger= LoggerFactory.getLogger(DeliverHandler.class);

    @Override
    public void onEvent(MessageSendEvent messageSendEvent, long l, boolean b) throws Exception {
        onEvent(messageSendEvent);
    }

    @Override
    public void onEvent(MessageSendEvent messageSendEvent) throws Exception {
        logger.debug("[DELIVER_MESSAGE]");
        if(messageSendEvent.getType()== MessageType.COMMAND_WEALTH_ACK){
            SendMessageRequest request=RequestHolder.getSendResponseCTX(messageSendEvent.getReceiverID());
            if(request!=null&&request.getChannel()!=null){
                logger.debug("[send  ack--->]");
                request.getChannel().write(messageSendEvent.getWriteData());
            }
        }else{
            SynchronizeRequest request=RequestHolder.getSynchronizedCTX(messageSendEvent.getReceiverID());
            if(request!=null&&request.getChannel()!=null&&request.getSynchronizeNum()<messageSendEvent.getMid()){
                logger.debug("[send  message--->]");
                request.getChannel().write(messageSendEvent.getWriteData());
            }
        }
    }

    public static DeliverHandler[] create(int num){
        DeliverHandler[] handlers=new DeliverHandler[num];
        for(int i=0;i<handlers.length;i++){
            handlers[i]=new DeliverHandler();
        }
        return handlers;
    }
}
