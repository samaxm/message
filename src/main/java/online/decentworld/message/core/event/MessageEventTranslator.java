package online.decentworld.message.core.event;

import com.lmax.disruptor.EventTranslatorOneArg;
import online.decentworld.message.core.MessageStatus;
import online.decentworld.message.security.validate.HttpSimpleValidateInfo;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/9/7.
 */
@Component(value = "messageEventTranslator")
public class MessageEventTranslator implements EventTranslatorOneArg<MessageReceiveEvent,TranslateInfo>
{



    @Override
    public void translateTo(MessageReceiveEvent messageReceiveEvent, long l, TranslateInfo translateInfo) {
        messageReceiveEvent.setStatus(MessageStatus.INITSTATUS());
        messageReceiveEvent.setData(translateInfo.getMsgData());
        messageReceiveEvent.setTempID(translateInfo.getTempID());
        messageReceiveEvent.setUserID(translateInfo.getUserID());
        messageReceiveEvent.setInfo(new HttpSimpleValidateInfo(translateInfo.getUserID(),translateInfo.getToken()));

    }

}
