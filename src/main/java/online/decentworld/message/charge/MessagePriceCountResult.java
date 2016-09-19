package online.decentworld.message.charge;

import online.decentworld.rpc.dto.message.types.ChatRelation;
import online.decentworld.rpc.dto.message.types.ChatStatus;

/**
 * Created by Sammax on 2016/9/14.
 */
public class MessagePriceCountResult extends P2PPriceCountResult
{
    private ChatRelation relation;
    private ChatStatus status;


    public MessagePriceCountResult(ConsumType type, int payerChargeAmount, int payeeChargeAmount, ChatRelation relation, ChatStatus status) {
        super(type, payerChargeAmount, payeeChargeAmount);
        this.relation = relation;
        this.status = status;
    }

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
