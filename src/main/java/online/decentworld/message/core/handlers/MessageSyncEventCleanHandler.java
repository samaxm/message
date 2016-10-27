package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.event.MessageSyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by Sammax on 2016/10/26.
 */
public class MessageSyncEventCleanHandler implements EventHandler<MessageSyncEvent>,WorkHandler<MessageSyncEvent> {
    private static Field[] fields;
    private static Logger logger = LoggerFactory.getLogger(MessageSyncEventCleanHandler.class);


    static {
        fields = MessageSyncEventCleanHandler.class.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
        }
    }

    @Override
    public void onEvent(MessageSyncEvent messageSyncEvent, long l, boolean b) throws Exception {
        onEvent(messageSyncEvent);
    }

    @Override
    public void onEvent(MessageSyncEvent messageSyncEvent) throws Exception {
        for (Field f : fields) {
            try {
                f.set(messageSyncEvent, null);
            } catch (Exception e) {
                logger.debug("", e);
            }
        }
    }
}