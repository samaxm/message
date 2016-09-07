package online.decentworld.message.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Sammax on 2016/9/7.
 */
public class ContextHolder {

    private static Logger logger= LoggerFactory.getLogger(ContextHolder.class);


    /**
     * send response context holder
     */
    private static ConcurrentHashMap<String,AsyncContext> res_ctx=new ConcurrentHashMap<>(10000);
    /**
     * sync response context holder
     */
    private static ConcurrentHashMap<String,AsyncContext> rsync_ctx=new ConcurrentHashMap<>(5000);


    public  static void storeSendResponseCTX(String key,AsyncContext ctx){
        logger.debug("[STORE CONTEXT] KEY#"+key);
        res_ctx.put(key,ctx);
    }

    public  static AsyncContext getSendResponseCTX(String key){
        return res_ctx.remove(key);
    }

    public static String getResponseKey(String dwID,String tempID){
        return dwID+tempID;
    }
}
