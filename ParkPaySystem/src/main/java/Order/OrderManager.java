package Order;


import Visitor.AbstractVistor;
import Visitor.Vistor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderManager {
    private List<Order> listOfOrders;
    private List<AbstractVistor> listOfVistor;

    private static AtomicInteger idGenerator = new AtomicInteger(100);

    public OrderManager(List<Order> listOfOrders) {
        this.listOfOrders = listOfOrders;
        this.listOfVistor = new ArrayList<AbstractVistor>();
    }

    public int createNewOrder(int pid, double amount, Vehicle vehicleInfo, PaymentInfo paymentInfo,
                              Date orderDate, String name, String email){
        int oid = idGenerator.getAndIncrement();
        AbstractVistor visitor = didVisitorAlreadyVisit(email);
        int vid;
        try{
            vid = visitor.getVid();
        }catch(NullPointerException e){
            vid = listOfVistor.size()+100;
            AbstractVistor newVistor = new Vistor(vid, name, email);
            listOfVistor.add(newVistor);
        }
        Order newOrder = new Order(oid, pid, vid, amount, vehicleInfo, paymentInfo, orderDate);
        listOfOrders.add(newOrder);

        return oid;
    }

    public JSONArray viewAllOrders(String key) {
        JSONArray orders = new JSONArray();
        for (Order order: listOfOrders){
            if(order.searchKey(key)){
                orders.put(order.viewOrder());
            }
        }
        return orders;
    }

    public JSONObject viewSpecificOrder(int oid) {
        JSONObject specificOrder ;
        JSONObject visitorInfo;
        Order order = returnOrder(oid);
        try {
            AbstractVistor visitor = returnVisitor(order.getVid());
            visitorInfo = visitor.viewVisitorInfo();
            visitorInfo.put("payment_info", order.viewPaymentInfo());

            specificOrder = order.viewOrder();
            specificOrder.remove("type");
            specificOrder.put("vid", Integer.toString(order.getVid()));
            specificOrder.put("vehicle", order.viewVehicleInfo());
            specificOrder.put("visitor", visitorInfo);
            specificOrder.put("payment_processing", order.viewProcessingInfo());
        }catch(NullPointerException e){
            specificOrder = new JSONObject();
        }
        return specificOrder;
    }


    public JSONArray viewVisitors(String key) {
        JSONArray visitors = new JSONArray();
        for(AbstractVistor visitor: listOfVistor){
            String v = visitor.viewVisitorInfo().toString();
            if(v.toLowerCase().contains(key.toLowerCase())){
                JSONObject visitorInfo = visitor.viewVisitorInfo();
                visitorInfo.put("vid", Integer.toString(visitor.getVid()));
                visitors.put(visitorInfo);
            }
        }
        return visitors;
    }

    public boolean checkIfVisitorVisitedPark(int pid, int vid) {
        boolean visited = false;
        Order order = getOrderForVid(vid);
        try {
            if (order.getVid() == vid && order.getPid() == pid) {
                visited = true;
            }
        }catch(NullPointerException e){

        }
        return visited;
    }

    public JSONObject viewSpecificVistors(int vid, JSONArray commentInfo) {
        JSONObject visitorInfo = new JSONObject();
        AbstractVistor visitor = returnVisitor(vid);
        try {
            visitorInfo.put("vid", Integer.toString(vid));
            JSONObject name_email = visitor.viewVisitorInfo();
            visitorInfo.put("visitor", name_email);
            JSONArray orderInfo = viewOrdersForVisitor(vid);
            visitorInfo.put("orders", orderInfo);
            visitorInfo.put("notes", commentInfo);

        }catch (NullPointerException e) {
            visitorInfo = new JSONObject();
        }
        return visitorInfo;
    }

    public JSONArray viewOrdersForVisitor(int vid) throws NullPointerException {
        JSONArray visitorsOrders = new JSONArray();
        Order order = getOrderForVid(vid);
        try {
            JSONObject orderDetails = order.viewOrder();
            orderDetails.remove("amount");
            orderDetails.remove("type");
            visitorsOrders.put(orderDetails);
        } catch (NullPointerException e){

        }
        return visitorsOrders;
    }

    private AbstractVistor didVisitorAlreadyVisit(String email) {
        for(AbstractVistor visitor:listOfVistor){
            if(visitor.emailSame(email)){
                return visitor;
            }
        }
        return null;
    }

    private Order getOrderForVid(int vid){
        for(Order o:listOfOrders){
            if(o.getVid() == vid){
                return o;
            }
        }
        return null;
    }

    public AbstractVistor returnVisitor(int vid) {
        for(AbstractVistor visitor:listOfVistor){
            if(visitor.getVid() == vid){
                return visitor;
            }
        }
        return null;
    }

    public Order returnOrder(int id){
        for(Order order:listOfOrders){
            if(order.getId() == id){
                return order;
            }
        }
        return null;
    }
}
