package online.decentworld.message.http;

import online.decentworld.message.cache.MessageCache;
import online.decentworld.message.cache.MessageSynchronizeResult;
import online.decentworld.message.common.MessageConfig;
import online.decentworld.rpc.codc.CodecHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sammax on 2016/9/19.
 */
@Service
public class SynchronizeService {
    @Autowired
    private MessageCache messageCache;


    public void handleSynchronizeRequest(SynchronizeRequest request){
        MessageSynchronizeResult result=messageCache.synchronizeMessage(request.getDwID(), request.getSynchronizeNum());
        List<byte[]> writable=new LinkedList<>();
        if(result!=null){
            List<byte[]> messaages=result.getMessages();
            List<byte[]> notices=result.getNotices();
            if(messaages!=null&&messaages.size()!=0){
                writable.addAll(messaages);
            }
            if(notices!=null&&notices.size()!=0){
                writable.addAll(notices);
            }
        }
        if(writable.size()!=0){
            request.getChannel().write(CodecHelper.toByteArray(writable, MessageConfig.SYSTEM_MESSAGE_SENDER,request.getDwID()));
        }else{
            request.startAsy();
            RequestHolder.storeSynchronizeRequest(request.getDwID(), request);
        }
    }

}
