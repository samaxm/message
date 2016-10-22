package online.decentworld.message.core;

import com.lmax.disruptor.EventTranslatorOneArg;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/10/22.
 */
@Component(value = "decodedMessageEventTranslator")
public class DecodedMessageEventTranslator implements EventTranslatorOneArg<MessageReceiveEvent,DecodedTranslateInfo>
{



    @Override
    public void translateTo(MessageReceiveEvent messageReceiveEvent, long l, DecodedTranslateInfo translateInfo) {
        messageReceiveEvent.setStatus(MessageStatus.INIT_DECODED());
        messageReceiveEvent.setMsg(translateInfo.getMessageWrapper());
        messageReceiveEvent.setTempID(translateInfo.getTempID());
        messageReceiveEvent.setUserID(translateInfo.getDwID());
    }
}
