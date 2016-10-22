package online.decentworld.message.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import online.decentworld.rpc.dto.message.protos.MessageProtos;

/**
 * Created by Sammax on 2016/10/15.
 */
public class TestHandler extends DebugLogHandler {


    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
            MessageProtos.Command_AuthChallengeResponse response=
                    MessageProtos.Command_AuthChallengeResponse.newBuilder().setResponse("111").setDwID("123").build();
            MessageProtos.Message message=MessageProtos.Message.newBuilder().setData(response.toByteString())
                    .setFrom("123").setTime(System.currentTimeMillis()).setTo("123").setType(MessageProtos.Message.MessageType.COMMAND_AUTH_CHALLENGE_RESPONSE).setMid(1).build();
            channelHandlerContext.writeAndFlush(message);
    }


}
