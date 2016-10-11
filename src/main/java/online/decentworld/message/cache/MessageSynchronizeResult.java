package online.decentworld.message.cache;

import java.util.List;

/**
 * Created by Sammax on 2016/9/19.
 */
public class MessageSynchronizeResult {



    private List<byte[]> messages;
    private List<byte[]> notices;

    public MessageSynchronizeResult(List<byte[]> messages, List<byte[]> notices) {
        this.messages = messages;
        this.notices = notices;
    }

    public List<byte[]> getMessages() {
        return messages;
    }

    public void setMessages(List<byte[]> messages) {
        this.messages = messages;
    }

    public List<byte[]> getNotices() {
        return notices;
    }

    public void setNotices(List<byte[]> notices) {
        this.notices = notices;
    }


}
