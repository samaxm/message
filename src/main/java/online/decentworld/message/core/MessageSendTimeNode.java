package online.decentworld.message.core;

import online.decentworld.message.core.session.Session;

/**
 * Created by Sammax on 2016/10/25.
 */
public final class MessageSendTimeNode {
    private long timestamp;
    private long syncNum;
    private Session session;


    public MessageSendTimeNode(long timestamp, long syncNum, Session session) {
        this.timestamp = timestamp;
        this.syncNum = syncNum;
        this.session = session;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getSyncNum() {
        return syncNum;
    }

    public void setSyncNum(long syncNum) {
        this.syncNum = syncNum;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
