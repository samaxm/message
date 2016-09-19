package online.decentworld.message.http;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;
import online.decentworld.message.cache.MessageCache;
import online.decentworld.message.cache.MessageSynchronizeResult;
import online.decentworld.message.config.Common;
import online.decentworld.message.core.MessageReceiveEvent;
import online.decentworld.message.core.TranslateInfo;
import online.decentworld.rpc.dto.api.StatusCode;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Sammax on 2016/9/7.
 */
@Controller
@RequestMapping(value = "/")
public class Message {

    private static Logger logger= LoggerFactory.getLogger(Message.class);

    @Resource(name = "messageDisruptor")
    private Disruptor<MessageReceiveEvent> disruptor;
    @Resource(name="messageEventTranslator")
    private EventTranslatorOneArg translator;
    @Autowired
    private MessageCache messageCache;


    @RequestMapping(value = "send")
    public void send(HttpServletRequest request,HttpServletResponse response){
        String token=null;
        byte[] data=null;
        String tempID=null;
        String userID=null;
        try {
            Collection<Part> parts=request.getParts();
            if(parts.size()!=4&&logger.isDebugEnabled()){
                logger.debug("ERROR PART NUM #"+parts.size());
            }else{
                for(Part part:parts){
                    if(part.getName().equals(Common.TOKEN_KEY)){
                        byte[] bytes=IOUtils.toByteArray(part.getInputStream());
                        token=new String(bytes,"utf-8");
                    }else if(part.getName().equals(Common.MSG_KEY)){
                        data=IOUtils.toByteArray(part.getInputStream());
                    }else if(part.getName().equals(Common.TEMP_ID)){
                        byte[] bytes=IOUtils.toByteArray(part.getInputStream());
                        tempID=new String(bytes,"utf-8");
                    }else if(part.getName().equals(Common.USER_ID)){
                        byte[] bytes=IOUtils.toByteArray(part.getInputStream());
                        userID=new String(bytes,"utf-8");
                    }
                }
                if(token!=null&&data!=null&&tempID!=null&&userID!=null){
                    AsyncContext ctx=request.startAsync();
                    ContextHolder.storeSendResponseCTX(ContextHolder.getResponseKey(userID,tempID),ctx);
                    TranslateInfo info=new TranslateInfo(token,data,tempID,userID);
                    disruptor.publishEvent(translator,info);

                }else {
                    logger.debug("[ERROR_DATA_FORMAT]");
                }
            }
        } catch (Exception e) {
            logger.debug("SEND FAILED",e);
            response.setStatus(StatusCode.FAILED);
            AsyncContext ctx=ContextHolder.getSendResponseCTX(ContextHolder.getResponseKey(userID, tempID));
            if(ctx!=null){
                try {
                    ctx.getResponse().getWriter().write("failed");
                    ctx.getResponse().getWriter().flush();
                } catch (IOException e1) {
                    logger.debug("[WRITE_RESPONSE_FAILED]",e1);
                }
            }
        }
    }



    @RequestMapping(value="sync")
    public void synchronizeMessage(long syncNum,String dwID,HttpServletRequest request,HttpServletResponse response){
        SynchronizeRequest sr=new SynchronizeRequest();
        MessageSynchronizeResult result=messageCache.synchronizeMessage(dwID, syncNum);
//        result.get
    }

}
