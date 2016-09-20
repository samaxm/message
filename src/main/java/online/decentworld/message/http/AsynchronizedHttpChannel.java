package online.decentworld.message.http;

import online.decentworld.message.core.channel.MessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Sammax on 2016/9/20.
 */
public class AsynchronizedHttpChannel implements MessageChannel {

    private String dwID;
    private AsyncContext ctx;
    private Logger logger= LoggerFactory.getLogger(AsynchronizedHttpChannel.class);

    public AsynchronizedHttpChannel(String dwID, AsyncContext ctx) {
        this.dwID = dwID;
        this.ctx = ctx;
    }

    @Override
    public String getUserID() {
        return dwID;
    }

    @Override
    public void write(byte[] data) {
        OutputStream outputStream=null;
        try{
            outputStream=ctx.getResponse().getOutputStream();
            outputStream.write(data);
            outputStream.flush();
        }catch (Exception e){
            logger.debug("[WRITE_FAILED] dwID#"+dwID,e);
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.debug("",e);
                }
            }
        }
    }
}
