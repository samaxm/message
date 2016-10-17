package online.decentworld.message.core;

/**
 * Created by Sammax on 2016/9/7.
 */
public class MessageStatus {

    private boolean isValidate;
    private boolean isPersistSuccessful;

    public static MessageStatus INITSTATUS(){
        return new MessageStatus(false,false);
    }
    public MessageStatus() {

    }

    public MessageStatus(boolean isValidate, boolean canDeliver) {
        this.isValidate = isValidate;
    }

    public boolean isValidate() {
        return isValidate;
    }

    public void setValidate(boolean isValidate) {
        this.isValidate = isValidate;
    }


    public boolean isPersistSuccessful() {
        return isPersistSuccessful;
    }

    public void setPersistSuccessful(boolean isPersistSuccessful) {
        this.isPersistSuccessful = isPersistSuccessful;
    }
}
