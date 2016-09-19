package online.decentworld.message.charge;

/**
 * Created by Sammax on 2016/9/9.
 */

public class Charger {

    private IChargeService chargeService;

    private PriceCounter priceCounter;

    public P2PChargeResult p2pCharge(P2PChargeEvent event){
        P2PPriceCountResult price= priceCounter.getP2PCost(event);
        P2PChargeResult result=chargeService.p2pCharge(event.getPayer(), event.getPayee(), price.getPayerChargeAmount(), price.getPayeeChargeAmount());
        return result;
    }

    public MessageChargeResult messageCharge(P2PChargeEvent event){
        MessagePriceCountResult price=priceCounter.getMessageCost(event);
        P2PChargeResult result=chargeService.p2pCharge(event.getPayer(), event.getPayee(), price.getPayerChargeAmount(), price.getPayeeChargeAmount());
        MessageChargeResult mr=new MessageChargeResult();
        mr.setRelation(price.getRelation());
        mr.setStatus(price.getStatus());
        mr.setStatusCode(result.getStatusCode());
        mr.setPayeeWealth(result.getPayeeWealth());
        mr.setPayerWealth(result.getPayerWealth());
        return mr;
    }

    public ChargeResult charge(ChargeEvent event){
        PriceCountResult price=priceCounter.getCost(event);
        ChargeResult result=chargeService.charge(price.getDwID(),price.getCost());
        result.setType(price.getType());
        return result;
    }


    public Charger(IChargeService chargeService, PriceCounter priceCounter) {
        this.chargeService = chargeService;
        this.priceCounter = priceCounter;
    }
}
