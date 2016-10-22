package online.decentworld.message.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.types.MessageType;

/**
 * Created by Sammax on 2016/10/22.
 */
public class CommandMessageHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageWrapper messageWrapper=(MessageWrapper)msg;
        if(messageWrapper.getType()== MessageType.COMMAND_MSG_SYNC_REQUEST){

        }
    }
}
