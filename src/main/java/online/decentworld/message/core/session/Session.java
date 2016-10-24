package online.decentworld.message.core.session;

import online.decentworld.message.core.channel.MessageChannel2;
import online.decentworld.message.core.sync.Sequences;

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

    Sequences getSequences();

    long getLastActiveTime();

    long setActiveTime(long time);

    int getOutMessageCount();

    int increaseOutMessageCount();

    int getInMessageCount();

    int increaseInMessageCount();

}
