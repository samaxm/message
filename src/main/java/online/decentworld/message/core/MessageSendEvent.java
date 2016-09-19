package online.decentworld.message.core;

import online.decentworld.rpc.dto.message.types.MessageType;

/**
 * Created by Sammax on 2016/9/14.
 */
public class MessageSendEvent {

    private byte[] writeData;
    private String receiverID;
    private MessageType type;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }


    public byte[] getWriteData() {
        return writeData;
    }

    public void setWriteData(byte[] writeData) {
        this.writeData = writeData;
    }


    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }
}
