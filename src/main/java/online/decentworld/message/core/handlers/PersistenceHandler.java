package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.MessageReceiveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/9/7.
 */
public class PersistenceHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent>  {

    private static Logger logger= LoggerFactory.getLogger(PersistenceHandler.class);

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {
        logger.debug("[SAVING TO DB---->]");
    }

    public static PersistenceHandler[] createGroup(int size){
        PersistenceHandler[] group=new PersistenceHandler[size];
        for(int i=0;i<size;i++){
            group[i]=new PersistenceHandler();
        }
        return group;
    }

}
