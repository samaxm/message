package online.decentworld.message.http;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;
import online.decentworld.message.config.Common;
import online.decentworld.message.core.event.MessageReceiveEvent;
import online.decentworld.message.core.event.TranslateInfo;
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
import java.util.Collection;

/**
 * Created by Sammax on 2016/9/7.
 */
@Controller
@RequestMapping(value = "/")
public class MessageController {

    private static Logger logger= LoggerFactory.getLogger(MessageController.class);

    @Resource(name = "messageDisruptor")
    private Disruptor<MessageReceiveEvent> disruptor;
    @Resource(name="messageReceiveEventTranslator")
    private EventTranslatorOneArg translator;

    @Autowired
    private SynchronizeService synchronizeService;

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
                    RequestHolder.storeSendRequest(RequestHolder.getResponseKey(userID, tempID),
                            new SendMessageRequest(userID,tempID,new AsynchronizedHttpChannel(userID,ctx)));
                    TranslateInfo info=new TranslateInfo(token,data,tempID,userID);
                    disruptor.publishEvent(translator,info);

                }else {
                    if(logger.isDebugEnabled()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        if (token == null) {
                            stringBuilder.append("token is null");
                        }
                        if (data == null) {
                            stringBuilder.append("data is null");
                        }
                        if (tempID == null) {
                            stringBuilder.append("tempID is null");
                        }
                        if (userID == null) {
                            stringBuilder.append("userID is null");
                        }
                        logger.debug("[ERROR_DATA_FORMAT] #"+stringBuilder.toString());
                    }
                    response.setStatus(400);

//                    response.getWriter().write(stringBuilder.toString().toCharArray());
//                    response.getWriter().flush();
                }
            }
        } catch (Exception e) {
            logger.debug("SEND FAILED",e);
        }
    }



    @RequestMapping(value="sync")
    public void synchronizeMessage(long syncNum,String dwID,HttpServletRequest request,HttpServletResponse response){
        logger.debug("[SYNC_MESSAGE] syncNum#"+syncNum+" dwID#"+dwID);
        SynchronizeRequest sr=new SynchronizeRequest(new SynchronizedHttpChannel(response,dwID),request,dwID,syncNum,false);
         synchronizeService.handleSynchronizeRequest(sr);
    }

}
