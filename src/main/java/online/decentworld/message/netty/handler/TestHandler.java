package online.decentworld.message.netty.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Sammax on 2016/10/15.
 */
public class TestHandler extends DebugLogHandler {


    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        super.channelRead(channelHandlerContext, o);
    }
}
