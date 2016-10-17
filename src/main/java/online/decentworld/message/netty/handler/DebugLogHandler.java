package online.decentworld.message.netty.handler;

import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * Created by Sammax on 2016/10/15.
 */
public class DebugLogHandler extends ChannelHandlerAdapter implements ChannelInboundHandler,ChannelOutboundHandler
{


    private Logger logger= LoggerFactory.getLogger(DebugLogHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        logger.debug("[channelRegistered]");
        channelHandlerContext.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        logger.debug("[channelUnregistered]");
        channelHandlerContext.fireChannelUnregistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        logger.debug("[channelActive]");
        channelHandlerContext.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        logger.debug("[channelInactive]");
        channelHandlerContext.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.debug("[channelRead]");
        channelHandlerContext.fireChannelRead(o);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        logger.debug("[channelReadComplete]");
        channelHandlerContext.fireChannelReadComplete();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.debug("[userEventTriggered]");
        channelHandlerContext.fireUserEventTriggered(o);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        logger.debug("[channelWritabilityChanged]");
        channelHandlerContext.fireChannelWritabilityChanged();
    }

    @Override
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        logger.debug("[bind]");
        channelHandlerContext.bind(socketAddress,channelPromise);
    }

    @Override
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress1, ChannelPromise channelPromise) throws Exception {
        logger.debug("[connect]");
        channelHandlerContext.connect(socketAddress,socketAddress1,channelPromise);
    }

    @Override
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        logger.debug("[disconnect]");
        channelHandlerContext.disconnect();
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        logger.debug("[close]");
        channelHandlerContext.close();
    }

    @Override
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        logger.debug("[deregister]");
        channelHandlerContext.deregister();
    }

    @Override
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        logger.debug("[read]");
        channelHandlerContext.read();
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object o, ChannelPromise channelPromise) throws Exception {
        logger.debug("[write]");
        channelHandlerContext.write(o,channelPromise);
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        logger.debug("[flush]");
        channelHandlerContext.flush();
    }
}
