package online.decentworld.message.core.session;

import online.decentworld.message.core.channel.MessageChannel2;

/**
 * Created by Sammax on 2016/10/20.
 */
public interface Session {

    String getIdentity();

    void setIdentity(String identity);

    String getChanllengeStrign();

    MessageChannel2 getMessageChannel();

    SessionStatus getSessionStatus();

    void setSessionStatus(SessionStatus sessionStatus);

    long getLastSyncTime();

    void setLastSyncTime();

    long getLastActiveTime();

    long activeSession();

    int getOutMessageCount();

    int increaseOutMessageCount();

    int getInMessageCount();

    int increaseInMessageCount();

    void closeSession();

}
