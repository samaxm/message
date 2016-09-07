package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import online.decentworld.message.core.MessageReceiveEvent;

/**
 * Created by Sammax on 2016/9/7.
 */
public class MessageEventHandler implements EventHandler<MessageReceiveEvent> {





    @Override
    public void onEvent(MessageReceiveEvent messageEvent, long l, boolean b) throws Exception {

    }
}
