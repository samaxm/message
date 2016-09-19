package online.decentworld.message.charge;

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
    public P2PChargeResult p2pCharge(String payerID, String payeeID, int payerChargeAmount, int payeeChargeAmount) {
        DBChargeResult payerResult=null;
        DBChargeResult payeeResult = null;
        P2PChargeResult result =new P2PChargeResult();
        if(payerChargeAmount!=0) {
            try {
                ZKLock.getWealthLock(payerID);
                payerResult = wealthMapper.charge(payerID, payerChargeAmount);
                result.setPayerWealth(payerResult.getNewWealth());
            } catch (Exception ex) {
                result.setStatusCode(ChargeResultCode.FAIL);
                logger.warn("", ex);
                return  result;
            } finally {
                ZKLock.unlockForExclusive(payerID);
            }
        }else{
            //flag -1 means no change
            result.setPayerWealth(-1);
        }
        if(payeeChargeAmount!=0) {
            try {
                ZKLock.getWealthLock(payeeID);
                payeeResult = wealthMapper.charge(payeeID, payeeChargeAmount);
                result.setPayeeWealth(payeeResult.getNewWealth());
            } catch (Exception ex) {
                result.setStatusCode(ChargeResultCode.FAIL);
                logger.warn("", ex);
                //TODO:payback
                return  result;
            } finally {
                ZKLock.unlockForExclusive(payerID);
            }
        }else {
            //flag -1 means no change
            result.setPayeeWealth(-1);
        }
        result.setStatusCode(ChargeResultCode.SUCCESS);
        return result;
    }

    @Override
    public ChargeResult charge(String dwID, int chargeAmount) {
        DBChargeResult dbResult = null;
        ChargeResult result =new ChargeResult();
        if(chargeAmount!=0) {
            try {
                ZKLock.getWealthLock(dwID);
                dbResult = wealthMapper.charge(dwID, chargeAmount);
                result.setNewWealth(dbResult.getNewWealth());
            } catch (Exception ex) {
                result.setStatusCode(ChargeResultCode.FAIL);
                logger.warn("", ex);
                return  result;
            } finally {
                ZKLock.unlockForExclusive(dwID);
            }
        }else{
            //flag -1 means no change
            result.setNewWealth(-1);
        }
        result.setStatusCode(ChargeResultCode.SUCCESS);
        return result;
    }



}
