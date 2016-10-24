package online.decentworld.message.core.channel;

import online.decentworld.rpc.dto.message.MessageWrapper;

/**
 * Created by Sammax on 2016/10/24.
 */
public interface MessageChannel2 {

    public void write(MessageWrapper messageWrapper);
}
