package online.decentworld.message.netty.handler;

import com.lmax.disruptor.dsl.Disruptor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import online.decentworld.message.core.MessageStatus;
import online.decentworld.message.core.event.MessageReceiveEvent;
import online.decentworld.message.core.session.Session;
import online.decentworld.message.netty.SessionKeys;
import online.decentworld.rpc.dto.message.ChatMessage;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Sammax on 2016/10/22.
 */
@ChannelHandler.Sharable
@Component(value ="chatMessageHandler" )
public class ChatMessageHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger= LoggerFactory.getLogger(ChatMessageHandler.class);

    @Resource(name = "messageDisruptor_v2")
    private Disruptor<MessageReceiveEvent> messageDisruptor;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageWrapper messageWrapper=(MessageWrapper)msg;
        if(MessageType.isChatMessage(messageWrapper.getType())){

            Session session=ctx.channel().attr(SessionKeys.SESSION).get();
            session.increaseInMessageCount();


            ChatMessage chatMessage=(ChatMessage)messageWrapper.getBody();
            String dwID=chatMessage.getFromID();
            String tempID=chatMessage.getTempID();
            logger.debug("[RECEIVE A CHAT] dwID#"+dwID+" type#"+chatMessage.getType());
            if(dwID.equals(ctx.channel().attr(SessionKeys.USER_ID).get())){
                messageDisruptor.publishEvent((MessageReceiveEvent messageReceiveEvent,long index)->{
                    messageReceiveEvent.setStatus(MessageStatus.INIT_DECODED());
                    messageReceiveEvent.setMsg(messageWrapper);
                    messageReceiveEvent.setTempID(tempID);
                    messageReceiveEvent.setUserID(dwID);
                });
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }
}
