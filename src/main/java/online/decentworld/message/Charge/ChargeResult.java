package online.decentworld.message.charge;

import online.decentworld.message.common.ChargeResultCode;

/**
 * Created by Sammax on 2016/9/9.
 */
public class ChargeResult {
    private ChargeResultCode statusCode;
    private int newWealth;
    private ConsumType type;

    public ChargeResultCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(ChargeResultCode statusCode) {
        this.statusCode = statusCode;
    }

    public int getNewWealth() {
        return newWealth;
    }

    public void setNewWealth(int newWealth) {
        this.newWealth = newWealth;
    }

    public ConsumType getType() {
        return type;
    }

    public void setType(ConsumType type) {
        this.type = type;
    }
}
