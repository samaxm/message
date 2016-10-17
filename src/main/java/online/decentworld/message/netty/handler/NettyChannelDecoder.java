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
        list.add(codec.decode(msg.array()));
//        ReferenceCountUtil.release(msg);
//        final byte[] array;
//        final int length = msg.readableBytes();
//        array = new byte[length];
//        msg.getBytes(msg.readerIndex(), array, 0, length);
//        MessageWrapper messageWrapper=codec.decode(array);
//        logger.debug("type#"+messageWrapper.getType());
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }

    public NettyChannelDecoder(Codec codec) {
        this.codec = codec;
    }
}
