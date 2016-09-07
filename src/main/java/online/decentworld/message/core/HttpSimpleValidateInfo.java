package online.decentworld.message.core;

/**
 * Created by Sammax on 2016/9/7.
 */
public class HttpSimpleValidateInfo implements ValidateInfo {
    final public static ValidateType type=ValidateType.HTTPBASE;
    private String token;
    private String userID;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HttpSimpleValidateInfo(String token) {
        this.token = token;
    }

    @Override
    public ValidateType getType() {
        return type;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
