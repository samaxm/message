package online.decentworld.message.http;

import online.decentworld.message.core.channel.MessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Sammax on 2016/9/20.
 */
public class SynchronizedHttpChannel implements MessageChannel {

    private HttpServletResponse response;
    private String dwID;
    private Logger logger= LoggerFactory.getLogger(SynchronizedHttpChannel.class);

    public SynchronizedHttpChannel(HttpServletResponse response, String dwID) {
        this.response = response;
        this.dwID = dwID;
    }

    @Override
    public String getUserID() {
        return dwID;
    }

    @Override
    public void write(byte[] data) {
        OutputStream outputStream=null;
        try{
            outputStream=response.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
        }catch (Exception e){
            logger.debug("[WRITE_FAILED] dwID#" + dwID, e);
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
