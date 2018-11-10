package test;

import Order.Order;
import App.PaymentInfo;
import App.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    Order newOrder;
    int oid = 101, pid = 201, vid = 109;
    double amount = 2.40;
    Vehicle vehicleInfo;
    PaymentInfo orderPayment;
    private Date timeStamp;
    String expiration = "12/19";

    @BeforeEach
    public void setup(){
        timeStamp = new Date();
        vehicleInfo = new Vehicle("IL", "Z567Z", "car");
        orderPayment = new PaymentInfo(60659, "4949", "John Doe", expiration);
        newOrder = new Order(oid, pid, vid,amount, vehicleInfo, orderPayment, timeStamp);
    }

    @Test
    public void viewOrder(){
        String OrderInfoJSONFormat = JSONFormatter();
        assertEquals("{"+OrderInfoJSONFormat+"}", newOrder.viewOrder().toString());
    }

    public String JSONFormatter(){
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String OrderInfoJSONFormat;
        OrderInfoJSONFormat = "\"date\":\"" + myDateFormat.format(timeStamp)+"\",\"amount\":"+amount+",\"pid\":"+pid;
        OrderInfoJSONFormat += ",\"oid\":" + oid +",\"type\":\""+vehicleInfo.getType()+"\"";
        return OrderInfoJSONFormat;
    }

    @Test
    public void viewVehicleInfo(){
        assertEquals("{\"plate\":\"Z567Z\",\"state\":\"IL\",\"type\":\"car\"}", newOrder.viewVehicleInfo().toString());
    }

    @Test
    public void viewPaymentInfo(){
        assertEquals("{\"zip\":60659,\"expiration_date\":\"12/19\",\"card\":\"4949\",\"name_on_card\":\"John Doe\"}",newOrder.viewPaymentInfo().toString());
    }

    @Test
    public void viewPaymentProcessingInfo(){
        DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        assertEquals("{\"date_and_time\":\""+myFormat.format(timeStamp)+"\",\"card_transaction_id\":\"123-4567-89\"}", newOrder.viewProcessingInfo().toString());
    }

    @Test
    public void testGetPid(){
        assertEquals(pid, newOrder.getPid());
    }
}
