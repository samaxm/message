package online.decentworld.message.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/10/18.
 */
public class ExceptionHandler implements ChannelHandler {

    private static Logger logger= LoggerFactory.getLogger(ExceptionHandler.class);


    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        logger.debug("[EXCEPTION]",throwable);
        channelHandlerContext.fireExceptionCaught(throwable);
    }
}
