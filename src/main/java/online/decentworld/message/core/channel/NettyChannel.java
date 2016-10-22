package online.decentworld.message.core.channel;

import io.netty.channel.Channel;
import online.decentworld.rpc.dto.message.MessageWrapper;

/**
 * Created by Sammax on 2016/10/20.
 */
public class NettyChannel {

    private Channel channel;

    public NettyChannel(Channel channel){
        this.channel=channel;
    }

    public Channel getChannel(){
        return channel;
    }

    public void write(MessageWrapper messageWrapper) {
        if(channel.eventLoop().inEventLoop()){
            channel.writeAndFlush(messageWrapper);
        }else{
            channel.eventLoop().submit(()->{
                channel.writeAndFlush(messageWrapper);
            });
        }
    }
}
