package online.decentworld.message.http;

import online.decentworld.message.core.MessageRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/9/12.
 */
public class HttpMessageRouter implements MessageRouter {

    private static Logger logger= LoggerFactory.getLogger(HttpMessageRouter.class);

    @Override
    public void route(byte[] data) {
//        logger.debug("[DELIVERING_MSG]");
//        String key=ContextHolder.getResponseKey(msg.getReceiver().getID(),messageReceiveEvent.getTempID());
//        AsyncContext ctx=ContextHolder.getSendResponseCTX(key);
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
