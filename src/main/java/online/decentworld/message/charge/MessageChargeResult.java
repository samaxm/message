package online.decentworld.message.charge;

import online.decentworld.rpc.dto.message.types.ChatRelation;
import online.decentworld.rpc.dto.message.types.ChatStatus;

/**
 * Created by Sammax on 2016/9/14.
 */
public class MessageChargeResult extends P2PChargeResult {
    private ChatRelation relation;
    private ChatStatus status;


    public ChatRelation getRelation() {
        return relation;
    }

    public void setRelation(ChatRelation relation) {
        this.relation = relation;
    }

    public ChatStatus getStatus() {
        return status;
    }

    public void setStatus(ChatStatus status) {
        this.status = status;
    }
}
