package Order;

public class PaymentInfo {
    private int zip;
    private String card;
    private String name;
    private String expiration;

    public PaymentInfo(int instanceZip, String instanceCard, String instanceName, String instanceExpiration){
        this.zip = instanceZip;
        this.card = instanceCard;
        this.name = instanceName;
        this.expiration = instanceExpiration;
    }


    public int getZip() {
        return this.zip;
    }

    public String getCard() {
        String maskedCard = maskCreditCard(this.card);
        return maskedCard;
    }

    private String maskCreditCard(String card) {
        String maskedCard = "xxxxxxxxxxxx";
        String stringLastFour = card.substring(card.length()-4);
        return maskedCard + stringLastFour;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        String dateExpiration = this.expiration;
        return dateExpiration;
    }
}
