package ParkPaySystem;

public class PaymentInfo {
    private int zip;
    private String card;

    public PaymentInfo(int instanceZip, String instanceCard){
        this.zip = instanceZip;
        this.card = instanceCard;
    }


    public int getZip() {
        return this.zip;
    }

    public String getCard() {
        return this.card;
    }
}
