package online.decentworld.message.netty.handler;

import com.lmax.disruptor.dsl.Disruptor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import online.decentworld.message.core.event.MessageSyncEvent;
import online.decentworld.message.core.session.Session;
import online.decentworld.message.netty.ChannelAttributeHelper;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.SyncRequestMessage;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/10/22.
 */
@ChannelHandler.Sharable
@Component(value = "syncCommandHandler")
public class SyncCommandHandler extends ChannelInboundHandlerAdapter {


    @Autowired
    private Disruptor<MessageSyncEvent> disruptor;



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageWrapper messageWrapper=(MessageWrapper)msg;
        MessageType type=messageWrapper.getType();
        if(type== MessageType.COMMAND_MSG_SYNC_REQUEST){
            Session session=ChannelAttributeHelper.getSession(ctx);
            String dwID=ChannelAttributeHelper.getUserID(ctx);
            disruptor.publishEvent((MessageSyncEvent syncEvent,long index)->{
                syncEvent.setDwID(dwID);
                syncEvent.setSession(session);
                syncEvent.setSyncNum(0);
            });
            session.setLastSyncTime();

        }else if(type==MessageType.COMMAND_MSG_SYNC_REQUEST){
            SyncRequestMessage sync=(SyncRequestMessage)messageWrapper.getBody();
            long syncedNum=sync.getSyncNum();
            Session session=ChannelAttributeHelper.getSession(ctx);
            String dwID=ChannelAttributeHelper.getUserID(ctx);
            disruptor.publishEvent((MessageSyncEvent syncEvent, long index) -> {
                syncEvent.setDwID(dwID);
                syncEvent.setSession(session);
                syncEvent.setSyncNum(syncedNum);
            });
            session.setLastSyncTime();
        }
    }
}
