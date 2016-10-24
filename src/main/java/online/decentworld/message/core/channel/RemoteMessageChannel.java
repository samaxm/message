package online.decentworld.message.core.channel;

import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.types.MessageType;
import online.decentworld.rpc.transfer.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/10/24.
 */
public class RemoteMessageChannel implements MessageChannel2 {

    private String remoteDomain;
    private String dwID;
    private Sender sender;

    private static Logger logger= LoggerFactory.getLogger(RemoteMessageChannel.class);


    public RemoteMessageChannel(String remoteDomain, String dwID,Sender sender) {
        this.remoteDomain = remoteDomain;
        this.dwID = dwID;
        this.sender=sender;
    }

    @Override
    public void write(MessageWrapper messageWrapper) {
        if(messageWrapper.getType()!= MessageType.COMMAND_WEALTH_ACK){
            messageWrapper=MessageWrapper.createCommand(MessageType.COMMAND_MSG_SYNC_NOTIFY);
        }
        try {
            sender.send(messageWrapper,remoteDomain);
        } catch (Exception e) {
            logger.debug("[SEND_REMOTE_MSG_FAILED] dwID#"+dwID+" remoteDomain#"+remoteDomain+" mid#"+messageWrapper.getMid(),e);
        }
    }


}
