package ParkPaySystem;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
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

    public void setVistorId(int vistorId){
        this.vistorId = vistorId;
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

}
