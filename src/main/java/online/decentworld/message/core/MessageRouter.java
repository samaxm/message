package online.decentworld.message.core;

/**
 * Created by Sammax on 2016/9/12.
 */
public interface MessageRouter {

    void route(byte[] msg);
}
