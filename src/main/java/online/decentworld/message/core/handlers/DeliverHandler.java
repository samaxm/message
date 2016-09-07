package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.MessageReceiveEvent;
import online.decentworld.message.http.ContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;

/**
 * Created by Sammax on 2016/9/7.
 */
public class DeliverHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent> {

    private static Logger logger= LoggerFactory.getLogger(DeliverHandler.class);


    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {
        logger.debug("[DELIVERING_MSG]");
        String key=ContextHolder.getResponseKey(messageReceiveEvent.getUserID(),messageReceiveEvent.getTempID());
        AsyncContext ctx=ContextHolder.getSendResponseCTX(key);
        if(ctx!=null){
            if(messageReceiveEvent.getStatus().isValidate()&&messageReceiveEvent.getStatus().isCanDeliver()){

                logger.debug("[DELIVERING_MSG_ONLINE]");
                ctx.getResponse().getWriter().write("test success");
                ctx.getResponse().getWriter().flush();
            }else {
                logger.debug("[DELIVERING_MSG_ONLINE]");
                ctx.getResponse().getWriter().write("test success money not enouth");
                ctx.getResponse().getWriter().flush();
            }
        }else{
            logger.debug("[LOST_CTX]");
        }

    }

    public static DeliverHandler[] createGroup(int size){
        DeliverHandler[] group=new DeliverHandler[size];
        for(int i=0;i<size;i++){
            group[i]=new DeliverHandler();
        }
        return group;
    }
}
