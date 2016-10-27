package online.decentworld.message.core.handlers;

import com.lmax.disruptor.ExceptionHandler;
import online.decentworld.message.core.event.MessageSyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/10/24.
 */
public class MessageSyncExceptionHandler implements ExceptionHandler<MessageSyncEvent> {

        private static Logger logger= LoggerFactory.getLogger(MessageSyncExceptionHandler.class);
        @Override
        public void handleEventException(Throwable throwable,long l,MessageSyncEvent messageSendEvent){
                logger.warn("",throwable);
                }

        @Override
        public void handleOnStartException(Throwable throwable){
                logger.warn("",throwable);
                }

        @Override
        public void handleOnShutdownException(Throwable throwable){
                logger.warn("",throwable);
                }
}