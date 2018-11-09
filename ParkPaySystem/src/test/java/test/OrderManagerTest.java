package test;

import Order.Order;
import Order.OrderManager;
import App.PaymentInfo;
import App.Vehicle;
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
    String name = "John Doe";
    String email = "djohn@gmail.com";
    PaymentInfo payment_info = new PaymentInfo(60659, "4949", name, "12/19");

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
        String acceptanceString = "[{";
        acceptanceString = addToString(acceptanceString);
        acceptanceString += "}]";

        buildOrderList(listOfOrders);

        myOrders = new OrderManager(listOfOrders);
        assertEquals(acceptanceString, myOrders.viewAllOrders().toString());
    }

    @Test
    public void viewOrderWhenManyOrdersPresent(){
        List<Order> listOfOrders = new ArrayList<Order>();
        String acceptanceString = "[{";
        acceptanceString = addToString(acceptanceString);
        buildOrderList(listOfOrders);
        acceptanceString += "},{";

        amount = 8.70; pid = 107; oid = 104; type = "RV";
        acceptanceString = addToString(acceptanceString);
        buildOrderList(listOfOrders);
        acceptanceString +="}]";

        myOrders = new OrderManager(listOfOrders);
        assertEquals(acceptanceString, myOrders.viewAllOrders().toString());
    }

    @Test
    public void viewSpecificOrder(){
        List<Order> listOfOrders = new ArrayList<Order>();
        buildOrderList(listOfOrders);
    }

    private void buildOrderList(List<Order> listOfOrders) {
        Order presentOrder = new Order(oid, pid, vid, amount, new Vehicle("IL","Z78Z", type), payment_info, timeStamp);
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
        int oid = myOrders.createNewOrder(pid, amount, new Vehicle("IL", "Z78Z", type), payment_info, timeStamp, name, email);

        assertNotEquals(-1, myOrders.returnIndex(oid));
    }

    @Test
    public void createNewOrder(){
        int newOid = myOrders.createNewOrder(pid, amount, new Vehicle("IL", "Z78Z", type), payment_info, timeStamp, name, email);
        assertNotEquals(-1, myOrders.returnIndex(newOid));
    }

    @Test
    public void viewSpecificOrderWhenNotThere(){
        assertEquals("{}", myOrders.viewSpecificOrder(1000).toString());
    }

    @Test
    public void viewSpecificOrderWhenPresent(){
        int newOid = myOrders.createNewOrder(pid, amount, new Vehicle("IL", "Z78Z", type), payment_info, timeStamp, name, email);
        oid = newOid;
        String acceptString = "{";
        acceptString = addToSpecificString(acceptString);
        acceptString += "}";

        assertEquals(acceptString,myOrders.viewSpecificOrder(newOid).toString());
    }

    private String addToString(String myString) {
        myString+="\"date\":\"" + formatter.format(timeStamp)+"\",\"amount\":"+amount+",\"pid\":"+pid;
        myString+=",\"oid\":" + oid +",\"type\":\""+type+"\"";
        return myString;
    }

    private String addToSpecificString(String myString){
        DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String specificVehicleInfo = "{\"plate\":\"Z78Z\",\"state\":\"IL\",\"type\":\"" + type+"\"}";
        String paymentInfo = "{\"zip\":60659,\"expiration_date\":\"12/19\",\"card\":\"4949\",\"name_on_card\":\""+name+"\"}";
        String visitorInfo = "{\"name\":\""+name+"\",\"payment_info\":" + paymentInfo+",\"email\":\""+email+"\"}";
        String processingInfo = "{\"date_and_time\":\""+newFormat.format(timeStamp)+"\",\"card_transaction_id\":\"123-4567-89\"}";

        myString+="\"date\":\"" + formatter.format(timeStamp)+"\",\"vid\":" + 100 +",\"amount\":"+amount+",\"pid\":"+pid;
        myString+=",\"oid\":" + oid;
        myString+= ",\"visitor\":" + visitorInfo +  ",\"payment_processing\":"+processingInfo+",\"vehicle\":" + specificVehicleInfo;
        return myString;
    }

    @Test
    public void viewAllVisitorsWithNoOrders(){
        assertEquals("[]", myOrders.viewVisitors().toString());
    }

    @Test
    public void viewAllVisitorsWhenPresent(){
        pid = 411;
        name = "John Doe";
        email = "john.doe@gmail.com";

        myOrders.createNewOrder(pid,amount,new Vehicle("IL", "Z7868", type), payment_info,timeStamp,name,email);
        assertEquals("[{\"vid\":100,\"name\":\"John Doe\",\"email\":\"john.doe@gmail.com\"}]", myOrders.viewVisitors().toString());
    }

    @Test
    public void viewSpecificVisitor(){
        assertEquals("{}", myOrders.viewSpecificVistors(500).toString());

        pid = 411;
        name = "John Doe";
        email = "john.doe@gmail.com";

        myOrders.createNewOrder(pid,amount,new Vehicle("IL", "Z7868", type), payment_info,timeStamp,name,email);

        assertEquals("{" +
                "\"vid\":100," +
                "\"notes\":[]," +
                "\"orders\":[{" +
                "\"oid\":100," +
                "\"pid\":102," +
                "\"date\":"+ formatter.format(new Date())+
                "}]," +
                "\"visitor\":{" +
                "\"name\":\"John Doe\"," +
                "\"email\":\"john.doe@example.com\"" +
                "}" +
                "}", myOrders.viewSpecificVistors(100).toString());
    }

    @Test
    public void testViewOrdersForVisitor(){
        assertEquals("[]", myOrders.viewOrdersForVisitor(500).toString());

        pid = 102;
        name = "John Doe";
        email = "john.doe@gmail.com";

        myOrders.createNewOrder(pid,amount,new Vehicle("IL", "Z7868", type), payment_info,timeStamp,name,email);


        assertEquals("[{" +
                "\"date\":\""+ formatter.format(new Date())+"\","+
                "\"pid\":102," +
                "\"oid\":100" +
                "}]", myOrders.viewOrdersForVisitor(100).toString());
    }
}
