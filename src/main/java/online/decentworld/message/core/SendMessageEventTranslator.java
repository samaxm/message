package online.decentworld.message.core;

import com.lmax.disruptor.EventTranslatorOneArg;

/**
 * Created by Sammax on 2016/9/14.
 */
public class SendMessageEventTranslator implements EventTranslatorOneArg<MessageSendEvent,MessageSendEventTranslateInfo> {
    @Override
    public void translateTo(MessageSendEvent messageSendEvent, long l, MessageSendEventTranslateInfo messageSendEventTranslateInfo) {
        messageSendEvent.setReceiverID(messageSendEventTranslateInfo.getReceiverID());
        messageSendEvent.setWriteData(messageSendEventTranslateInfo.getWriteData());
        messageSendEvent.setType(messageSendEventTranslateInfo.getMessageType());
        messageSendEvent.setMid(messageSendEventTranslateInfo.getMid());
    }
}
