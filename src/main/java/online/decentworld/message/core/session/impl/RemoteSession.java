package online.decentworld.message.core.session.impl;

import online.decentworld.message.core.channel.MessageChannel2;
import online.decentworld.message.core.channel.RemoteMessageChannel;
import online.decentworld.message.core.session.Session;
import online.decentworld.message.core.session.SessionStatus;
import online.decentworld.message.core.sync.Sequences;
import online.decentworld.rpc.transfer.Sender;

/**
 * Created by Sammax on 2016/10/24.
 */
public class RemoteSession implements Session {


    private String remoteDomain;
    private String dwID;
    private Sender sender;

    public RemoteSession(String remoteDomain,String dwID,Sender sender) {
        this.remoteDomain = remoteDomain;
        this.dwID=dwID;
        this.sender=sender;
    }

    @Override
    public String getIdentity() {
        return dwID;
    }

    @Override
    public void setIdentity(String identity) {
        this.dwID=identity;
    }



    @Override
    public MessageChannel2 getMessageChannel() {
        return new RemoteMessageChannel(remoteDomain,dwID,sender);
    }

    @Override
    public SessionStatus getSessionStatus() {
        throw new UnsupportedOperationException("remote session don't support");
    }



    @Override
    public String getChanllengeStrign() {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public void setSessionStatus(SessionStatus sessionStatus) {
        throw new UnsupportedOperationException("remote session don't support");
    }

    @Override
    public Sequences getSequences() {
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
        throw new UnsupportedOperationException("remote session don't support");
    }

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
