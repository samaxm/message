package online.decentworld.message.cache;

import java.util.List;

/**
 * Created by Sammax on 2016/9/19.
 */
public class MessageSynchronizeResult {



    private List<byte[]> messages;

    public MessageSynchronizeResult(List<byte[]> messages) {
        this.messages = messages;
    }

    public List<byte[]> getMessages() {
        return messages;
    }

    public void setMessages(List<byte[]> messages) {
        this.messages = messages;
    }
}
