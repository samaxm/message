package online.decentworld.message.core.session;

import online.decentworld.message.core.channel.NettyChannel;
import online.decentworld.message.exception.SessionConfictException;

/**
 * Created by Sammax on 2016/10/20.
 */
public interface SessionManager {

    public Session createSession(NettyChannel channel);

    public void activeSession(String channelID,String dwID) throws SessionConfictException;

    public void receiveSessionPing(String dwID);

    public Session getSession(String dwID);
}
