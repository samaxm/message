package online.decentworld.message.core.event;

import online.decentworld.rpc.dto.message.MessageWrapper;

/**
 * Created by Sammax on 2016/10/24.
 */
public class MessageDispatchEvent {


    private String receiverID;
    private MessageWrapper message;

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public MessageWrapper getMessage() {
        return message;
    }

    public void setMessage(MessageWrapper message) {
        this.message = message;
    }
}
