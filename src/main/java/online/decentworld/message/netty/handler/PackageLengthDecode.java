package online.decentworld.message.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import online.decentworld.message.netty.ChannelAttributeHelper;

import java.util.List;

/**
 * Created by Sammax on 2016/11/28.
 */
public class PackageLengthDecode extends ProtobufVarint32FrameDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(ChannelAttributeHelper.needLengthDecode(channelHandlerContext)){
            super.decode(channelHandlerContext,byteBuf,list);
        }
    }
}
