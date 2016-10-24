package online.decentworld.message.core.channel;

import online.decentworld.message.util.MessagePusher;
import online.decentworld.rpc.dto.message.MessageWrapper;

/**
 * Created by Sammax on 2016/10/24.
 */
public class OfflineMessageChannel implements MessageChannel2 {

    private MessagePusher messagePusher;
    private String APNsToken;

    public OfflineMessageChannel(MessagePusher messagePusher, String APNsToken) {
        this.messagePusher = messagePusher;
        this.APNsToken = APNsToken;
    }

    @Override
    public void write(MessageWrapper messageWrapper) {
        String notice;
        switch (messageWrapper.getType()){
            case CHAT_TEXT:
                notice="文字消息";
                break;
            case CHAT_AUDIO:
                notice="语音消息";
                break;
            case CHAT_IMAGE:
                notice="图片消息";
                break;
            default:
                notice="系统通知";
        }
        messagePusher.pushMessage(notice,APNsToken);
    }
}
