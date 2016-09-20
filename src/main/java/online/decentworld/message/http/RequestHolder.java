package online.decentworld.message.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Sammax on 2016/9/7.
 */
public class RequestHolder {

    private static Logger logger= LoggerFactory.getLogger(RequestHolder.class);

    /**
     * send response context holder
     */
    private static ConcurrentHashMap<String,SendMessageRequest> res_ctx=new ConcurrentHashMap<>(10000);
    /**
     * sync response context holder
     */
    private static ConcurrentHashMap<String,SynchronizeRequest> rsync_ctx=new ConcurrentHashMap<>(5000);


    public  static void storeSendRequest(String key,SendMessageRequest request){
        logger.debug("[STORE CONTEXT] KEY#"+key);
        res_ctx.put(key,request);
    }

    public static void storeSynchronizeRequest(String dwID,SynchronizeRequest request){
        logger.debug("[STORE_SYNCHRONIZE_REQUEST] dwID#"+dwID);
        rsync_ctx.put(dwID,request);
    }

    public  static SendMessageRequest getSendResponseCTX(String key){
        return res_ctx.remove(key);
    }
    public  static SynchronizeRequest getSynchronizedCTX(String key){
        return rsync_ctx.remove(key);
    }
    public static String getResponseKey(String dwID,String tempID){
        return dwID+tempID;
    }
}
