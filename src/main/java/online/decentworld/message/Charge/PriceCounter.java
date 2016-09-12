package online.decentworld.message.Charge;

/**
 * Created by Sammax on 2016/9/9.
 */

public interface PriceCounter {

    public P2PPriceCountResult getP2PCost(ChargeEvent event);
    public PriceCountResult getCost(ChargeEvent event);
}
