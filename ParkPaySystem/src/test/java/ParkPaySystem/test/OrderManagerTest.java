package ParkPaySystem.test;

import ParkPaySystem.Order;
import ParkPaySystem.OrderManager;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderManagerTest {
    OrderManager myOrders;

    @BeforeEach
    public void setup(){
        List<Order> listOfOrders = new ArrayList<Order>();
        myOrders = new OrderManager(listOfOrders);
    }

    @Test
    public void viewAllOrdersWhenNoOrdersPresent(){
        JSONArray allOrders = myOrders.viewAllOrders();
        assertEquals("[]", allOrders.toString());
    }

    @Test void viewAllOrdersWhenOrdersArePresent(){
        int oid = 751;
        int pid = 124;
        Date timeStamp = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String type = "car";
        double amount = 4.50;
        String acceptanceString = "[{\"oid\":"+oid+",\"pid\":"+pid+",\"date\":"+formatter.format(timeStamp)+",\"type\":"+type+",\"amount\":"+amount+"}]";
        List<Order> listOfOrders = new ArrayList<Order>();
        Order presentOrder = new Order(oid, pid, amount, 60649, "IL", "Z2121", type, "John@yahoo.com", "4949","John Adams", "07/21");
        listOfOrders.add(presentOrder);
        myOrders = new OrderManager(listOfOrders);
        assertEquals(acceptanceString, myOrders.viewAllOrders().toString());
    }

}
