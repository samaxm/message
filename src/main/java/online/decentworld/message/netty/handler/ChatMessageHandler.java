package online.decentworld.message.netty.handler;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import online.decentworld.message.core.DecodedTranslateInfo;
import online.decentworld.message.core.MessageReceiveEvent;
import online.decentworld.message.core.session.SessionManager;
import online.decentworld.message.netty.SessionKeys;
import online.decentworld.rpc.dto.message.ChatMessage;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Sammax on 2016/10/22.
 */
@ChannelHandler.Sharable
@Component(value ="chatMessageHandler" )
public class ChatMessageHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger= LoggerFactory.getLogger(ChatMessageHandler.class);

    @Autowired
    private Disruptor<MessageReceiveEvent> messageDisruptor;
    @Resource(name="decodedMessageEventTranslator")
    private EventTranslatorOneArg translator;
    @Autowired
    private SessionManager sessionManager;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageWrapper messageWrapper=(MessageWrapper)msg;
        if(MessageType.isChatMessage(messageWrapper.getType())){
            ChatMessage chatMessage=(ChatMessage)messageWrapper.getBody();
            String dwID=chatMessage.getFromID();
            String tempID=chatMessage.getTempID();
            logger.debug("[RECEIVE A CHAT] dwID#"+dwID+" type#"+chatMessage.getType());
            if(dwID.equals(ctx.channel().attr(SessionKeys.USER_ID).get())){
                messageDisruptor.publishEvent(translator,new DecodedTranslateInfo(messageWrapper,dwID,tempID));
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }
}
