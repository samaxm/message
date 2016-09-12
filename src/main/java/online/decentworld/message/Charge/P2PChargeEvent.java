package online.decentworld.message.Charge;

/**
 * Created by Sammax on 2016/9/9.
 */
public class P2PChargeEvent implements ChargeEvent {

    private String payer;
    private String payee;
    private ConsumType type;

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public void setType(ConsumType type) {
        this.type = type;
    }

    public P2PChargeEvent(String payer, String payee) {
        this.payer = payer;
        this.payee = payee;
    }

    @Override
    public ConsumType getConsumType() {
        return null;
    }
}
