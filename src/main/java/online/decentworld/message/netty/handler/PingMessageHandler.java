package online.decentworld.message.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import online.decentworld.message.core.session.SessionManager;
import online.decentworld.message.netty.ChannelAttributeHelper;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/10/22.
 */
@ChannelHandler.Sharable
@Component(value ="pingMessageHandler" )
public class PingMessageHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger= LoggerFactory.getLogger(PingMessageHandler.class);
    @Autowired
    private SessionManager sessionManager;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof MessageWrapper&&((MessageWrapper) msg).getType()==MessageType.COMMAND_PING){
            sessionManager.receiveSessionPing(ChannelAttributeHelper.getUserID(ctx));
        }else{
            ctx.fireChannelRead(msg);
        }
    }
}
