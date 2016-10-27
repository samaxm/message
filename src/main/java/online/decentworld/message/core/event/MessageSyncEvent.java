package online.decentworld.message.core.event;

import online.decentworld.message.core.session.Session;

/**
 * Created by Sammax on 2016/10/25.
 */
public class MessageSyncEvent {
    private String dwID;
    private Session session;
    private long syncNum;

    public MessageSyncEvent(String dwID, Session session,long syncNum) {
        this.dwID = dwID;
        this.session = session;
        this.syncNum=syncNum;
    }
    public MessageSyncEvent(){}
    public String getDwID() {
        return dwID;
    }

    public void setDwID(String dwID) {
        this.dwID = dwID;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public long getSyncNum() {
        return syncNum;
    }

    public void setSyncNum(long syncNum) {
        this.syncNum = syncNum;
    }
}
