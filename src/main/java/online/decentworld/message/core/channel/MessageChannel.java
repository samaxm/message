package online.decentworld.message.core.channel;

/**
 * Created by Sammax on 2016/9/20.
 */
public interface MessageChannel {
    String getUserID();
    void write(byte[] data);

}
