package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.event.MessageReceiveEvent;
import online.decentworld.message.core.MessageStatus;
import online.decentworld.rpc.codc.Codec;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Sammax on 2016/9/8.
 */
@Component
public class DecodeHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent> {


    @Resource(name = "protosCodec")
    private Codec codec;


    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {
        MessageStatus status=messageReceiveEvent.getStatus();
        if(status.isValidate()&&!status.isDecoded()) {
            messageReceiveEvent.setMsg(codec.decode(messageReceiveEvent.getData()));
            messageReceiveEvent.setData(null);
        }
    }
}
