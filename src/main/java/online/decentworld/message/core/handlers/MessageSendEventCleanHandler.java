package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.MessageSendEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by Sammax on 2016/9/20.
 */
public class MessageSendEventCleanHandler  implements EventHandler<MessageSendEvent>,WorkHandler<MessageSendEvent> {
    private static Field[] fields;
    private static Logger logger= LoggerFactory.getLogger(MessageSendEventCleanHandler.class);


    static {
        fields=MessageSendEvent.class.getDeclaredFields();
        for(Field f:fields){
            f.setAccessible(true);
        }
    }

    @Override
    public void onEvent(MessageSendEvent messageSendEvent, long l, boolean b) throws Exception {
        onEvent(messageSendEvent);
    }

    @Override
    public void onEvent(MessageSendEvent messageSendEvent) throws Exception {
        for(Field f:fields){
            try {
                f.set(messageSendEvent,null);
            }catch (Exception e){
                logger.debug("",e);
            }
        }
    }


}
