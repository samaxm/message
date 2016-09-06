package online.decentworld.message.core;

import online.decentworld.rpc.dto.message.BaseMessage;

/**
 * Created by Sammax on 2016/9/6.
 */
public class MessageEvent {

//    private BaseMessage msg;
    private long msg;

    public void set(long msg){
        this.msg=msg;
    }
}
