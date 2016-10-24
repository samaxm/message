package online.decentworld.message.core.channel;

import io.netty.channel.Channel;
import online.decentworld.message.core.session.SessionStatus;
import online.decentworld.message.core.session.impl.LocalSession;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.types.MessageType;

/**
 * Created by Sammax on 2016/10/20.
 */
public class LocalNettyChannel implements MessageChannel2 {

    private Channel channel;
    private LocalSession localSession;

    public LocalNettyChannel(Channel channel){
        this.channel=channel;
    }

    public Channel getChannel(){
        return channel;
    }

    public void write(MessageWrapper messageWrapper) {
        if(messageWrapper.getType()!= MessageType.COMMAND_WEALTH_ACK){
            messageWrapper=MessageWrapper.createCommand(MessageType.COMMAND_MSG_SYNC_NOTIFY);
            doWrite(messageWrapper);
        }else{
            if(localSession.getSessionStatus()== SessionStatus.SYNC){
                return;
            }
            doWrite(messageWrapper);
        }

    }

    public LocalSession getLocalSession() {
        return localSession;
    }

    public void setLocalSession(LocalSession localSession) {
        this.localSession = localSession;
    }

    private void doWrite(MessageWrapper messageWrapper){
        if(MessageType.isChatMessage(messageWrapper.getType())){
            localSession.increaseInMessageCount();
        }


        if(channel.eventLoop().inEventLoop()){
            channel.writeAndFlush(messageWrapper);
        }else{
            final MessageWrapper finalMessageWrapper = messageWrapper;
            channel.eventLoop().submit(()->{
                channel.writeAndFlush(finalMessageWrapper);
            });
        }
    }
}
