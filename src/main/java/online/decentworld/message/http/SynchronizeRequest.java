package online.decentworld.message.http;

import online.decentworld.message.core.channel.MessageChannel;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Sammax on 2016/9/19.
 */
public class SynchronizeRequest {

    private MessageChannel channel;
    private HttpServletRequest request;
    private String dwID;
    private long synchronizeNum;
    private boolean isAsynchronized=false;

    public SynchronizeRequest(MessageChannel channel, HttpServletRequest request, String dwID, long synchronizeNum, boolean isAsynchronized) {
        this.channel = channel;
        this.request = request;
        this.dwID = dwID;
        this.synchronizeNum = synchronizeNum;
        this.isAsynchronized = isAsynchronized;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public void setChannel(MessageChannel channel) {
        this.channel = channel;
    }

    public void startAsy(){
        if(!isAsynchronized){
            isAsynchronized=true;
            AsyncContext ctx=request.startAsync();
            this.setChannel(new AsynchronizedHttpChannel(dwID,ctx));
        }
    }

    public long getSynchronizeNum() {
        return synchronizeNum;
    }

    public void setSynchronizeNum(long synchronizeNum) {
        this.synchronizeNum = synchronizeNum;
    }

    public String getDwID() {
        return dwID;
    }

    public void setDwID(String dwID) {
        this.dwID = dwID;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
