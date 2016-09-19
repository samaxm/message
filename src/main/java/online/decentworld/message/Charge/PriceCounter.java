package online.decentworld.message.charge;

/**
 * Created by Sammax on 2016/9/9.
 */

public interface PriceCounter {
    public MessagePriceCountResult getMessageCost(ChargeEvent event);
    public P2PPriceCountResult getP2PCost(ChargeEvent event);
    public PriceCountResult getCost(ChargeEvent event);
}
