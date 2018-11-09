package Order;

import App.PaymentInfo;
import App.Vehicle;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    int id, parkId, vistorId;
    double amount;
    Date dateOfTransaction;
    Vehicle vehicleInfo;
    PaymentInfo orderPayment;
    String transactionId;

    public Order(int id, int parkId, int vistorId, double amount, Vehicle vehicleInfo, PaymentInfo orderPayment, Date orderDate){
        this.id = id;
        this.parkId = parkId;
        this.vistorId = vistorId;
        this.amount = amount;
        this.vehicleInfo = vehicleInfo;
        this.orderPayment = orderPayment;
        this.dateOfTransaction = orderDate;
    }

    public int getId() {
        return this.id;
    }

    public JSONObject viewOrder() {
        JSONObject orderInfo = new JSONObject();
        DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        orderInfo.put("oid",this.id);
        orderInfo.put("pid", this.parkId);
        orderInfo.put("amount", this.amount);
        orderInfo.put("type", this.vehicleInfo.getType());
        orderInfo.put("date", myFormat.format(this.dateOfTransaction));

        return orderInfo;
    }

    public int getVid() {
        return this.vistorId;
    }

    public JSONObject viewVehicleInfo() {
        JSONObject vehicleInfoJSON = new JSONObject();
        vehicleInfoJSON.put("state", this.vehicleInfo.getState());
        vehicleInfoJSON.put("plate", this.vehicleInfo.getPlate());
        vehicleInfoJSON.put("type", this.vehicleInfo.getType());

        return vehicleInfoJSON;
    }

    public JSONObject viewPaymentInfo() {
        JSONObject paymentInfo = new JSONObject();
        paymentInfo.put("zip", this.orderPayment.getZip());
        paymentInfo.put("name_on_card", this.orderPayment.getName());
        paymentInfo.put("expiration_date", this.orderPayment.getDate());
        paymentInfo.put("card", this.orderPayment.getCard());
        return paymentInfo;
    }

    public JSONObject viewProcessingInfo() {
        DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        JSONObject processingInfo = new JSONObject();
        processingInfo.put("card_transaction_id", "123-4567-89");
        processingInfo.put("date_and_time", myFormat.format(this.dateOfTransaction));
        return processingInfo;
    }

    public int getPid() {
        return this.parkId;
    }
}
