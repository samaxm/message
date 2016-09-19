package online.decentworld.message.core;

import online.decentworld.rpc.dto.message.types.MessageType;

/**
 * Created by Sammax on 2016/9/14.
 */
public class MessageSendEventTranslateInfo {
    private byte[] writeData;
    private String receiverID;
    private MessageType messageType;


    public MessageSendEventTranslateInfo(byte[] writeData, String receiverID, MessageType messageType) {
        this.writeData = writeData;
        this.receiverID = receiverID;
        this.messageType = messageType;
    }

    public MessageSendEventTranslateInfo() {
    }


    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
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
