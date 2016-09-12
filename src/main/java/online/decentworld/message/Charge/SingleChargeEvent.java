package online.decentworld.message.Charge;

/**
 * Created by Sammax on 2016/9/9.
 */
public class SingleChargeEvent implements ChargeEvent {

    private ConsumType type;
    private String dwID;

    public ConsumType getType() {
        return type;
    }

    public String getDwID() {
        return dwID;
    }

    public void setDwID(String dwID) {
        this.dwID = dwID;
    }

    @Override
    public ConsumType getConsumType() {
        return type;
    }
}
