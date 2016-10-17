package online.decentworld.message.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import online.decentworld.rpc.codc.Codec;
import online.decentworld.rpc.dto.message.protos.MessageProtos;

/**
 * Created by Sammax on 2016/10/15.
 */
public class DefaultChannelInitiallizer extends ChannelInitializer<NioSocketChannel> {



    private Codec codec;

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline().addLast(new DebugLogHandler());
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
//        ch.pipeline().addLast(new NettyChannelDecoder(codec));
        ch.pipeline().addLast(new ProtobufEncoder());
        ch.pipeline().addLast(new ProtobufDecoder(MessageProtos.Message.getDefaultInstance()));
        ch.pipeline().addLast(new TestHandler());
//        ch.pipeline().addLast(new NettyChannelEncoder(codec));
    }


    public void setCodec(Codec codec) {
        this.codec = codec;
    }
}
