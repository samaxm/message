package online.decentworld.message.Charge;

import online.decentworld.message.common.ChargeResultCode;
import online.decentworld.message.security.lock.ZKLock;
import online.decentworld.rdb.entity.DBChargeResult;
import online.decentworld.rdb.mapper.WealthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sammax on 2016/9/8.
 */
@Service
public class ChargeService implements IChargeService {

    private static Logger logger= LoggerFactory.getLogger(ChargeService.class);
    @Autowired
    private WealthMapper wealthMapper;


    @Override
    public ChargeResult p2pCharge(String payerID, String payeeID, int amount) {
        DBChargeResult payerResult=null;
        DBChargeResult payeeResult = null;
        try{
            ZKLock.getWealthLock(payerID);
            payerResult =wealthMapper.charge(payerID, -Math.abs(amount));
        }catch (Exception ex){
            logger.warn("",ex);
            throw ex;
        }finally {
            ZKLock.unlockForExclusive(payerID);
        }

        try{
            ZKLock.getWealthLock(payeeID);
            payeeResult= wealthMapper.charge(payeeID,Math.abs(amount));
        }catch (Exception ex){
            logger.warn("",ex);
            //TODO:payback
        }finally {
            ZKLock.unlockForExclusive(payerID);
        }
        ChargeResult result =new ChargeResult();
        result.setStatusCode(ChargeResultCode.SUCCESS);
        result.setPayeeWealth(payeeResult.getNewWealth());
        result.setPayerWealth(payerResult.getNewWealth());
        return result;
    }


}
