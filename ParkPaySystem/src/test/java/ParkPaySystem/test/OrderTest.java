package ParkPaySystem.test;

import ParkPaySystem.Order;
import ParkPaySystem.PaymentInfo;
import ParkPaySystem.Vehicle;
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

    @BeforeEach
    public void setup(){
        timeStamp = new Date();
        vehicleInfo = new Vehicle("IL", "Z567Z", "car");
        orderPayment = new PaymentInfo(60659, "8080");
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


}
