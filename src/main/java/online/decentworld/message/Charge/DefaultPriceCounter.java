package online.decentworld.message.Charge;

/**
 * Created by Sammax on 2016/9/12.
 */
public class DefaultPriceCounter implements PriceCounter{


    @Override
    public P2PPriceCountResult getP2PCost(ChargeEvent event) {
        if(event.getConsumType()==ConsumType.CHAT){
            return new P2PPriceCountResult(ConsumType.CHAT,-1,1);
        }else {
            return null;
        }
    }

    @Override
    public PriceCountResult getCost(ChargeEvent event) {
        return null;
    }
}
