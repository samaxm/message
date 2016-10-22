package online.decentworld.message.exception;

/**
 * Created by Sammax on 2016/10/22.
 */
public class SessionConfictException extends Exception {
    private String dwID;


    public SessionConfictException(String dwID) {
        super(dwID+" already login");
        this.dwID = dwID;
    }


}
