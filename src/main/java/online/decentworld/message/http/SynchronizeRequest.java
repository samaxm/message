package online.decentworld.message.http;

import javax.servlet.AsyncContext;

/**
 * Created by Sammax on 2016/9/19.
 */
public class SynchronizeRequest {
    private AsyncContext ctx;
    private long synchronizeNum;

    public AsyncContext getCtx() {
        return ctx;
    }

    public void setCtx(AsyncContext ctx) {
        this.ctx = ctx;
    }

    public long getSynchronizeNum() {
        return synchronizeNum;
    }

    public void setSynchronizeNum(long synchronizeNum) {
        this.synchronizeNum = synchronizeNum;
    }
}
