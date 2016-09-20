package online.decentworld.message.core.handlers;

import com.lmax.disruptor.ExceptionHandler;
import online.decentworld.message.core.MessageSendEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/9/19.
 */
public class DefaultMessageSendExceptionHandler implements ExceptionHandler<MessageSendEvent> {
    private static Logger logger= LoggerFactory.getLogger(DefaultMessageSendExceptionHandler.class);
    @Override
    public void handleEventException(Throwable throwable, long l, MessageSendEvent messageSendEvent) {
        logger.warn("",throwable);
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        logger.warn("",throwable);
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        logger.warn("",throwable);
    }
}
