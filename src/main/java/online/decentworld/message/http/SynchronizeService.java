package online.decentworld.message.http;

import online.decentworld.message.cache.MessageCache;
import online.decentworld.message.cache.MessageSynchronizeResult;
import online.decentworld.message.common.MessageConfig;
import online.decentworld.rpc.codc.CodecHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sammax on 2016/9/19.
 */
@Service
public class SynchronizeService {
    @Autowired
    private MessageCache messageCache;

    private static Logger logger= LoggerFactory.getLogger(SynchronizeService.class);

    public void handleSynchronizeRequest(SynchronizeRequest request){
        MessageSynchronizeResult result=messageCache.synchronizeMessage(request.getDwID(), request.getSynchronizeNum());
        if(result!=null){
            List<byte[]> messaages=result.getMessages();
            if(messaages!=null&&messaages.size()!=0){
                logger.debug("[WRITE]");
                request.getChannel().write(CodecHelper.toByteArray(messaages, MessageConfig.SYSTEM_MESSAGE_SENDER, request.getDwID()));
            }else{
                logger.debug("[WAIT]");
                request.startAsy();
                RequestHolder.storeSynchronizeRequest(request.getDwID(), request);
            }
        }

    }

}
