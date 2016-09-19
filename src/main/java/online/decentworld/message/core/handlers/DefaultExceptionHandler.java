package online.decentworld.message.core.handlers;

import com.lmax.disruptor.ExceptionHandler;
import online.decentworld.message.core.MessageReceiveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/9/19.
 */
public class DefaultExceptionHandler implements ExceptionHandler<MessageReceiveEvent> {
    private static Logger logger= LoggerFactory.getLogger(DefaultExceptionHandler.class);
    @Override
    public void handleEventException(Throwable throwable, long l, MessageReceiveEvent messageReceiveEvent) {
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
