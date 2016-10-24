package online.decentworld.message.core.event;

/**
 * Created by Sammax on 2016/9/7.
 */
public class TranslateInfo {

    private String token;
    private byte[] msgData;
    private String tempID;
    private String userID;

    public TranslateInfo(String token, byte[] msgData,String tempID,String userID) {
        this.token = token;
        this.msgData = msgData;
        this.tempID=tempID;
        this.userID=userID;
    }


    public String getTempID() {
        return tempID;
    }


    public String getToken() {
        return token;
    }


    public byte[] getMsgData() {
        return msgData;
    }


    public String getUserID() {
        return userID;
    }

}
