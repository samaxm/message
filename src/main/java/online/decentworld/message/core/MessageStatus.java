package online.decentworld.message.core;

/**
 * Created by Sammax on 2016/9/7.
 */
public class MessageStatus {

    private boolean isValidate;

    private boolean isDecoded;
    private boolean isPersistSuccessful;



    public boolean isDecoded() {
        return isDecoded;
    }

    public void setDecoded(boolean isDecoded) {
        this.isDecoded = isDecoded;
    }


    public static MessageStatus INITSTATUS(){
        return new MessageStatus(false,false,false);
    }
    public static MessageStatus INIT_DECODED(){
        return new MessageStatus(true,false,true);
    }
    public MessageStatus() {

    }

    public MessageStatus(boolean isValidate, boolean isPersistSuccessful,boolean isDecoded) {
        this.isValidate = isValidate;
        this.isPersistSuccessful=isPersistSuccessful;
        this.isDecoded=isDecoded;
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
