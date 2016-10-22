package online.decentworld.message.core;

import online.decentworld.rpc.dto.message.MessageWrapper;

/**
 * Created by Sammax on 2016/10/22.
 */
public class DecodedTranslateInfo {

    private MessageWrapper messageWrapper;
    private String dwID;
    private String tempID;


    public DecodedTranslateInfo(MessageWrapper messageWrapper, String dwID, String tempID) {
        this.messageWrapper = messageWrapper;
        this.dwID = dwID;
        this.tempID = tempID;
    }

    public MessageWrapper getMessageWrapper() {
        return messageWrapper;
    }

    public void setMessageWrapper(MessageWrapper messageWrapper) {
        this.messageWrapper = messageWrapper;
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
}
