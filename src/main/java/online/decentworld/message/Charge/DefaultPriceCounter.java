package online.decentworld.message.charge;

import online.decentworld.rpc.dto.message.types.ChatRelation;
import online.decentworld.rpc.dto.message.types.ChatStatus;

/**
 * Created by Sammax on 2016/9/12.
 */
public class DefaultPriceCounter implements PriceCounter{


    @Override
    public MessagePriceCountResult getMessageCost(ChargeEvent event) {
        return new MessagePriceCountResult(ConsumType.CHAT,-1,1, ChatRelation.STRANGER, ChatStatus.NORMAL);
    }

    @Override
    public P2PPriceCountResult getP2PCost(ChargeEvent event) {
        if(event.getConsumType()==ConsumType.CHAT){
            return new MessagePriceCountResult(ConsumType.CHAT,-1,1, ChatRelation.STRANGER, ChatStatus.NORMAL);
        }else {
            return null;
        }
    }

    @Override
    public PriceCountResult getCost(ChargeEvent event) {
        return null;
    }
}
