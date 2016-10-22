package online.decentworld.message.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import online.decentworld.rpc.codc.Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Sammax on 2016/10/15.
 */
public class NettyChannelDecoder extends ByteToMessageDecoder
{
    private static Logger logger= LoggerFactory.getLogger(NettyChannelDecoder.class);

    private Codec codec;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msg, List<Object> list) throws Exception {
        logger.debug("[DECODING]");
        byte[] bytes=new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        list.add(codec.decode(bytes));
    }

    public NettyChannelDecoder(Codec codec) {
        this.codec = codec;
    }
}
