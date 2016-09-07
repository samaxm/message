package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.MessageReceiveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/9/7.
 */
public class ChargeHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent> {

    private static Logger logger= LoggerFactory.getLogger(ChargeHandler.class);

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {

        if(Integer.valueOf(messageReceiveEvent.getTempID())%2==0){
            logger.debug("[CHARGING--->ENOUGH MONEY] tempID#"+messageReceiveEvent.getTempID());
            messageReceiveEvent.getStatus().setCanDeliver(true);
        }else{
            logger.debug("[CHARGING--->NOT ENOUGH MONEY] tempID#"+messageReceiveEvent.getTempID());
            messageReceiveEvent.getStatus().setCanDeliver(false);
        }
    }

    public static ChargeHandler[] createGroup(int size){
        ChargeHandler[] group=new ChargeHandler[size];
        for(int i=0;i<size;i++){
            group[i]=new ChargeHandler();
        }
        return group;
    }
}
