package online.decentworld.message.Charge;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sammax on 2016/9/8.
 */
public interface IChargeService {
    @Transactional
    public ChargeResult p2pCharge(String payerID,String payeeID,int amount);

}
