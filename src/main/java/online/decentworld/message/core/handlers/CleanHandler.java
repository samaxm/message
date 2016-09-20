package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.MessageReceiveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by Sammax on 2016/9/7.
 */
public class CleanHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent> {

    private static Logger logger= LoggerFactory.getLogger(CleanHandler.class);
    private static Field[] fields=MessageReceiveEvent.class.getDeclaredFields();

    static{
        for(Field field:fields){
            field.setAccessible(true);
        }
    }
    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {
        logger.debug("[RESET_EVENT_FIELD]");
        resetFields(messageReceiveEvent);
    }

    private void resetFields(MessageReceiveEvent messageReceiveEvent){
        for(Field f:fields){
            try{
                f.set(messageReceiveEvent,null);
            }catch (Exception e){
                logger.warn("",e);
            }
        }
    }
}
