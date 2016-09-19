package online.decentworld.message.charge;

/**
 * Created by Sammax on 2016/9/9.
 */
public class P2PPriceCountResult  {


    private ConsumType type;
    private int payerChargeAmount;

    public int getPayeeChargeAmount() {
        return payeeChargeAmount;
    }

    public void setPayeeChargeAmount(int payeeChargeAmount) {
        this.payeeChargeAmount = payeeChargeAmount;
    }

    public int getPayerChargeAmount() {
        return payerChargeAmount;
    }

    public void setPayerChargeAmount(int payerChargeAmount) {
        this.payerChargeAmount = payerChargeAmount;
    }

    private int payeeChargeAmount;


    public ConsumType getType() {
        return null;
    }

    public void setType(ConsumType type) {
        this.type = type;
    }

    public P2PPriceCountResult(ConsumType type, int payerChargeAmount, int payeeChargeAmount) {
        this.type = type;
        this.payerChargeAmount = payerChargeAmount;
        this.payeeChargeAmount = payeeChargeAmount;
    }
}
