package online.decentworld.message.Charge;

import online.decentworld.message.common.ChargeResultCode;

/**
 * Created by Sammax on 2016/9/7.
 */
public class ChargeResult {
    public ChargeResultCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(ChargeResultCode statusCode) {
        this.statusCode = statusCode;
    }

    private ChargeResultCode statusCode;
    private int payerWealth;
    private int payeeWealth;


    public int getPayerWealth() {
        return payerWealth;
    }

    public void setPayerWealth(int payerWealth) {
        this.payerWealth = payerWealth;
    }

    public int getPayeeWealth() {
        return payeeWealth;
    }

    public void setPayeeWealth(int payeeWealth) {
        this.payeeWealth = payeeWealth;
    }
}
