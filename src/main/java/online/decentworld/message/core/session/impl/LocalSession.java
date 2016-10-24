package online.decentworld.message.core.session.impl;

import online.decentworld.message.core.channel.MessageChannel2;
import online.decentworld.message.core.channel.LocalNettyChannel;
import online.decentworld.message.core.session.Session;
import online.decentworld.message.core.session.SessionStatus;
import online.decentworld.message.core.sync.Sequences;
import online.decentworld.message.core.sync.impl.BasicSequences;

/**
 * Created by Sammax on 2016/10/20.
 */
public class LocalSession implements Session {

    private String dwID;
    private LocalNettyChannel channel;
    private String channllengeString;
    private SessionStatus status;
    private Sequences sequences;
    private long lastActiveTime;
    private int outMessageCount;
    private int inMessageCount;

    public LocalSession(String dwID, LocalNettyChannel channel, String channllengeString) {
        this.dwID = dwID;
        this.channel = channel;
        this.channllengeString = channllengeString;
        this.sequences=new BasicSequences();
        this.status=SessionStatus.CHALLENGE;
        this.lastActiveTime=System.currentTimeMillis();
        this.outMessageCount=this.inMessageCount=0;
        channel.setLocalSession(this);
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
    public String getChanllengeStrign() {
        return channllengeString;
    }

    @Override
    public MessageChannel2 getMessageChannel() {
        return channel;
    }

    @Override
    public SessionStatus getSessionStatus() {
        return status;
    }

    @Override
    public void setSessionStatus(SessionStatus sessionStatus) {
        this.status=sessionStatus;
    }

    @Override
    public Sequences getSequences() {
        return sequences;
    }

    @Override
    public long getLastActiveTime() {
        return lastActiveTime;
    }

    @Override
    public long setActiveTime(long time) {
        return lastActiveTime=time;
    }

    @Override
    public int getOutMessageCount() {
        return outMessageCount;
    }

    @Override
    public int increaseOutMessageCount() {
        return outMessageCount++;
    }

    @Override
    public int getInMessageCount() {
        return inMessageCount;
    }

    @Override
    public int increaseInMessageCount() {
        return inMessageCount++;
    }
}
