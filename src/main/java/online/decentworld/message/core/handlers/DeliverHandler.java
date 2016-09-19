package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.MessageSendEvent;
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
//        AsyncContext ctx=null;
//        if(messageSendEvent.getType()== MessageType.WEALTH_ACK){
//            ctx=ContextHolder.getSendResponseCTX(messageSendEvent.getReceiverID());
//        }else{
//            ctx=ContextHolder.getSynchronizedCTX(messageSendEvent.getReceiverID());
//        }
//        if(ctx!=null){
//            if(messageReceiveEvent.getStatus().isValidate()&&messageReceiveEvent.getStatus().isCanDeliver()){
//                logger.debug("[DELIVERING_MSG_ONLINE]");
//                ctx.getResponse().getWriter().write("test success");
//                ctx.getResponse().getWriter().flush();
//            }else {
//                logger.debug("[DELIVERING_MSG_ONLINE]");
//                ctx.getResponse().getWriter().write("test success money not enouth");
//                ctx.getResponse().getWriter().flush();
//            }
//        }else{
//            logger.debug("[LOST_CTX]");
//        }
    }
}
