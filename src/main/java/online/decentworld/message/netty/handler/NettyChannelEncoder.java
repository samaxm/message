package online.decentworld.message.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import online.decentworld.rpc.codc.Codec;
import online.decentworld.rpc.dto.message.MessageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/10/15.
 */
public class NettyChannelEncoder extends MessageToByteEncoder<MessageWrapper> {

    private Logger logger= LoggerFactory.getLogger(NettyChannelEncoder.class);

    private Codec codec;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageWrapper wrapper, ByteBuf byteBuf) throws Exception {
        logger.debug("[ENCODING]");
        byteBuf.writeBytes(codec.encode(wrapper));
    }

    public NettyChannelEncoder(Codec codec){
        this.codec=codec;
    }

}
