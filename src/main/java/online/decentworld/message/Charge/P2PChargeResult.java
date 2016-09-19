package online.decentworld.message.charge;

import online.decentworld.message.common.ChargeResultCode;

/**
 * Created by Sammax on 2016/9/7.
 */
public class P2PChargeResult {

    private ChargeResultCode statusCode;
    private int payerWealth;
    private int payeeWealth;
    private ConsumType type;

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

    public ChargeResultCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(ChargeResultCode statusCode) {
        this.statusCode = statusCode;
    }

    public ConsumType getType() {
        return type;
    }

    public void setType(ConsumType type) {
        this.type = type;
    }
}
