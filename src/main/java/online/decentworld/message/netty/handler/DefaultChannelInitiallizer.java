package online.decentworld.message.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import online.decentworld.rpc.codc.Codec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Sammax on 2016/10/15.
 */

@Component(value = "defaultChannelInitiallizer")
public class DefaultChannelInitiallizer extends ChannelInitializer<NioSocketChannel> {


    @Autowired
    private Codec codec;
    @Resource(name = "authHandler")
    private ChannelHandler authHandler;


    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline().addLast(new DebugLogHandler());
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        ch.pipeline().addLast(new NettyChannelDecoder(codec));
//      ch.pipeline().addLast(new ProtobufEncoder());
        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        ch.pipeline().addLast(new NettyChannelEncoder(codec));
        ch.pipeline().addLast(authHandler);
//      ch.pipeline().addLast(new TestHandler());
    }


    public void setCodec(Codec codec) {
        this.codec = codec;
    }
}

