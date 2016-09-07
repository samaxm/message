package online.decentworld.message.core;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorTwoArg;
import online.decentworld.rpc.codc.Codec;
import online.decentworld.rpc.dto.message.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Sammax on 2016/9/7.
 */
@Component
public class MessageEventTranslator implements EventTranslatorOneArg<MessageReceiveEvent,TranslateInfo>
{

    @Resource(name = "protosCodec")
    private Codec codec;


    @Override
    public void translateTo(MessageReceiveEvent messageReceiveEvent, long l, TranslateInfo translateInfo) {
        messageReceiveEvent.setStatus(MessageStatus.INITSTATUS());
        messageReceiveEvent.setInfo(new HttpSimpleValidateInfo(translateInfo.getToken()));
        messageReceiveEvent.setMsg(codec.decode(translateInfo.getMsgData()));
        messageReceiveEvent.setTempID(translateInfo.getTempID());
        messageReceiveEvent.setUserID(translateInfo.getUserID());
    }
}
