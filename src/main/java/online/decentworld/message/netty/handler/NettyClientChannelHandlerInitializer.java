package online.decentworld.message.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by Sammax on 2016/10/17.
 */
public class NettyClientChannelHandlerInitializer extends ChannelInitializer<NioSocketChannel>{

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline().addLast(new DebugLogHandler());
        ch.pipeline().addLast(new PackageLengthDecode());
        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        ch.pipeline().addLast(new ProtobufEncoder());
//        ch.pipeline().addLast(new ProtobufDecoder(MessageProtos.Message.getDefaultInstance()));

    }
}
