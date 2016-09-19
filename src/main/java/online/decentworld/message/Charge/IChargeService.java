package online.decentworld.message.charge;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sammax on 2016/9/8.
 */
public interface IChargeService {
    @Transactional
    public P2PChargeResult p2pCharge(String payerID,String payeeID, int payerChargeAmount, int payeeChargeAmount);


    @Transactional
    public ChargeResult charge(String dwID,int chargeAmount);


}
