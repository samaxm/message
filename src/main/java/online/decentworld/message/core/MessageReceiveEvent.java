package online.decentworld.message.core;

import online.decentworld.message.Charge.ChargeResult;
import online.decentworld.message.security.validate.ValidateInfo;
import online.decentworld.rpc.dto.message.BaseMessage;

/**
 * field can never be write by multi consumer concurrently!
 */
public class MessageReceiveEvent {

    /**
     * message status
     */
    private MessageStatus status;
    /**
     * message
     */
    private BaseMessage msg;
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
    private ChargeResult chargeResult;
    /**
     * receive data
     */
    private byte[] data;

    public BaseMessage getMsg() {
        return msg;
    }

    public void setMsg(BaseMessage msg) {
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

    public ChargeResult getChargeResult() {
        return chargeResult;
    }

    public void setChargeResult(ChargeResult chargeResult) {
        this.chargeResult = chargeResult;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
