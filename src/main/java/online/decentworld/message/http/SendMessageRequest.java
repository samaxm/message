package online.decentworld.message.http;

import online.decentworld.message.core.channel.MessageChannel;

/**
 * Created by Sammax on 2016/9/20.
 */
public class SendMessageRequest {
    private String dwID;
    private String tempID;
    private MessageChannel channel;

    public SendMessageRequest(String dwID, String tempID, MessageChannel channel) {
        this.dwID = dwID;
        this.tempID = tempID;
        this.channel = channel;
    }

    public String getDwID() {
        return dwID;
    }

    public void setDwID(String dwID) {
        this.dwID = dwID;
    }

    public String getTempID() {
        return tempID;
    }

    public void setTempID(String tempID) {
        this.tempID = tempID;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public void setChannel(MessageChannel channel) {
        this.channel = channel;
    }
}
