package online.decentworld.message.core.event;

import online.decentworld.charge.receipt.MessageReceipt;
import online.decentworld.message.core.MessageStatus;
import online.decentworld.message.security.validate.ValidateInfo;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.WealthAckMessage;

/**
 * same field can never be write by multi consumer concurrently!
 */
public class MessageReceiveEvent {

    /**
     * message status
     */
    private MessageStatus status;
    /**
     * message
     */
    private MessageWrapper msg;
    /**
     * validate info
     */
    private ValidateInfo info;
    /**
     * temp id for the message to identify response
     */
    private String tempID;
    /**
     * user who send the msg
     */
    private String userID;
    /**
     * charge result if the msg need to be charged
     */
    private MessageReceipt messageReceipt;
    /**
     * receive data
     */
    private byte[] data;
    /**
     * wealth ack message
     */
    private WealthAckMessage wealthAckMessage;

    public WealthAckMessage getWealthAckMessage() {
        return wealthAckMessage;
    }

    public void setWealthAckMessage(WealthAckMessage wealthAckMessage) {
        this.wealthAckMessage = wealthAckMessage;
    }

    public MessageWrapper getMsg() {
        return msg;
    }

    public void setMsg(MessageWrapper msg) {
        this.msg = msg;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public ValidateInfo getInfo() {
        return info;
    }

    public void setInfo(ValidateInfo info) {
        this.info = info;
    }

    public String getTempID() {
        return tempID;
    }

    public void setTempID(String tempID) {
        this.tempID = tempID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public MessageReceipt getMessageReceipt() {
        return messageReceipt;
    }

    public void setMessageReceipt(MessageReceipt messageReceipt) {
        this.messageReceipt = messageReceipt;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
