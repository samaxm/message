package online.decentworld.message.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import online.decentworld.message.core.session.SessionManager;
import online.decentworld.message.netty.ChannelAttributeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/10/28.
 */
@ChannelHandler.Sharable
@Component("cleanResource")
public class CleanResourceHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private SessionManager sessionManager;

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        sessionManager.removeSession(ChannelAttributeHelper.getUserID(ctx));
        super.channelUnregistered(ctx);
    }
}
