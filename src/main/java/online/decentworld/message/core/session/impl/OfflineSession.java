package online.decentworld.message.core.session.impl;

import online.decentworld.message.core.channel.MessageChannel2;
import online.decentworld.message.core.channel.OfflineMessageChannel;
import online.decentworld.message.core.session.Session;
import online.decentworld.message.core.session.SessionStatus;
import online.decentworld.message.util.MessagePusher;

/**
 * Created by Sammax on 2016/10/24.
 */
public class OfflineSession implements Session {




    private MessagePusher messagePusher;
    private String APNsToken;


    public OfflineSession(MessagePusher messagePusher, String APNsToken) {
        this.messagePusher = messagePusher;
        this.APNsToken = APNsToken;
    }

    @Override
    public String getIdentity() {
        return APNsToken;
    }

    @Override
    public void setIdentity(String identity) {
        this.APNsToken=identity;
    }

    @Override
    public String getChanllengeStrign() {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public MessageChannel2 getMessageChannel() {
        return new OfflineMessageChannel(messagePusher,APNsToken);
    }

    @Override
    public SessionStatus getSessionStatus() {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public void setSessionStatus(SessionStatus sessionStatus) {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public long getLastSyncTime() {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public void setLastSyncTime() {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public long getLastActiveTime() {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public long setActiveTime(long time) {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public int getOutMessageCount() {
        throw new UnsupportedOperationException("remote session don't support");    }

    @Override
    public int increaseOutMessageCount() {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public int getInMessageCount() {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public int increaseInMessageCount() {
        throw new UnsupportedOperationException("remote session don't support");
    }
}
