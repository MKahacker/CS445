package ParkPaySystem.test;

import ParkPaySystem.Order;
import ParkPaySystem.OrderManager;
import ParkPaySystem.PaymentInfo;
import ParkPaySystem.Vehicle;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrderManagerTest {
    OrderManager myOrders;
    Date timeStamp = new Date();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    int oid = 751, pid = 124,vid = 109;
    String type = "car";
    double amount = 4.50;

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

    @Test
    public void viewAllOrdersWhenOneOrderisPresent(){
        List<Order> listOfOrders = new ArrayList<Order>();
        String acceptanceString = "[";
        acceptanceString = addToString(acceptanceString);
        acceptanceString += "]";

        buildOrderList(listOfOrders);

        myOrders = new OrderManager(listOfOrders);
        assertEquals(acceptanceString, myOrders.viewAllOrders().toString());
    }

    @Test
    public void viewOrderWhenManyOrdersPresent(){
        List<Order> listOfOrders = new ArrayList<Order>();
        String acceptanceString = "[";
        acceptanceString = addToString(acceptanceString);
        buildOrderList(listOfOrders);
        acceptanceString += ",";

        amount = 8.70; pid = 107; oid = 104; type = "RV";
        acceptanceString = addToString(acceptanceString);
        buildOrderList(listOfOrders);
        acceptanceString +="]";

        myOrders = new OrderManager(listOfOrders);
        assertEquals(acceptanceString, myOrders.viewAllOrders().toString());
    }

    private String addToString(String myString){
        myString+="{\"date\":\"" + formatter.format(timeStamp)+"\",\"amount\":"+amount+",\"pid\":"+pid;
        myString+=",\"oid\":" + oid +",\"type\":\""+type+"\"}";
        return myString;
    }

    @Test
    public void viewSpecificOrder(){
        List<Order> listOfOrders = new ArrayList<Order>();
        buildOrderList(listOfOrders);
    }

    private void buildOrderList(List<Order> listOfOrders) {
        Order presentOrder = new Order(oid, pid, vid, amount, new Vehicle("IL","Z78Z", type), new PaymentInfo(60659, "4949"), timeStamp);
        listOfOrders.add(presentOrder);
    }

    @Test
    public void returnIndexReturnsMinusOneForNonExistantIDs(){
        assertEquals(-1, myOrders.returnIndex(1000));
    }

    @Test
    public void returnIndexForId(){
        String name = "John Doe";
        String email = "djohn@gmail.com";
        int oid = myOrders.createNewOrder(pid, amount, new Vehicle("IL", "Z78Z", type), new PaymentInfo(60669, "4949"), timeStamp, name, email);

        assertNotEquals(-1, myOrders.returnIndex(oid));
    }

    @Test
    public void createNewOrder(){
        String name = "John Doe";
        String email = "djohn@gmail.com";
        int oid = myOrders.createNewOrder(pid, amount, new Vehicle("IL", "Z78Z", type), new PaymentInfo(60669, "4949"), timeStamp, name, email);
        assertNotEquals(-1, myOrders.returnIndex(oid));
    }

    @Test
    public void viewSpecificOrderWhenNotThere(){}



}
