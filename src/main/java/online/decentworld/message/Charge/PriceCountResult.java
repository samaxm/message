package online.decentworld.message.charge;

/**
 * Created by Sammax on 2016/9/9.
 */
public class PriceCountResult {
    private String dwID;
    private ConsumType type;
    private int cost;

    public String getDwID() {
        return dwID;
    }

    public void setDwID(String dwID) {
        this.dwID = dwID;
    }

    public ConsumType getType() {
        return type;
    }

    public void setType(ConsumType type) {
        this.type = type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
