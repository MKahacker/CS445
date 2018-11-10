package Order;

import App.PaymentInfo;
import App.Vehicle;
import Order.Order;
import Visitor.AbstractVistor;
import Visitor.Vistor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManager {
    private List<Order> listOfOrders;
    private List<AbstractVistor> listOfVistor;

    public OrderManager(List<Order> listOfOrders) {
        this.listOfOrders = listOfOrders;
        this.listOfVistor = new ArrayList<AbstractVistor>();
    }

    public int createNewOrder(int pid, double amount, Vehicle vehicleInfo, PaymentInfo paymentInfo,
                              Date orderDate, String name, String email){
        int oid = listOfOrders.size() + 100;
        int vid = didVisitorAlreadyVisit(email);
        if(vid == -1) {
            vid = listOfVistor.size() + 100;
            AbstractVistor newVistor = new Vistor(vid, name, email);
            listOfVistor.add(newVistor);
        }
        Order newOrder = new Order(oid, pid, vid, amount, vehicleInfo, paymentInfo, orderDate);
        listOfOrders.add(newOrder);

        return oid;
    }

    private int didVisitorAlreadyVisit(String email) {
        int vid = -1;
        for(int i=0; i < this.listOfVistor.size(); i++){
            if(getVisitor(i).getEmail().equals(email)){
                vid = getVisitor(i).getVid();
            }
        }
        return vid;
    }

    public JSONArray viewAllOrders() {
        JSONArray orders = new JSONArray();
        for (int i = 0; i < this.listOfOrders.size(); i++)
            orders.put(listOfOrders.get(i).viewOrder());
        return orders;
    }

    public JSONObject viewSpecificOrder(int oid) {
        JSONObject specificOrder = new JSONObject();
        JSONObject visitorInfo = new JSONObject();
        int idx = returnIndex(oid), vidx;
        if(idx !=  -1){
            vidx = returnVistorIndex(getOrder(idx).getVid());
            visitorInfo = getVisitor(vidx).viewVisitorInfo();
            visitorInfo.put("payment_info", getOrder(idx).viewPaymentInfo());
            specificOrder = getOrder(idx).viewOrder();
            specificOrder.remove("type");
            specificOrder.put("vid", getOrder(idx).getVid());
            specificOrder.put("vehicle", getOrder(idx).viewVehicleInfo());
            specificOrder.put("visitor", visitorInfo);
            specificOrder.put("payment_processing", getOrder(idx).viewProcessingInfo());
        }
        return specificOrder;
    }

    private int returnVistorIndex(int vid) {
        int idx = -1;
        for(int i = 0; i < this.listOfVistor.size(); i++){
            if(getVisitor(i).getVid() == vid){
                idx = i;
                return idx;
            }
        }
        return idx;
    }

    public int returnIndex(int id){
        int idx = -1;
        for(int i = 0; i < this.listOfOrders.size(); i++){
            if(getOrder(i).getId() == id){
                idx = i;
                return idx;
            }
        }
        return idx;
    }

    private Order getOrder(int i) {
        return this.listOfOrders.get(i);
    }

    private AbstractVistor getVisitor(int idx) {
        return this.listOfVistor.get(idx);
    }

    public JSONArray viewVisitors() {
        JSONArray visitorsInfo = new JSONArray();
        for(int i = 0; i < this.listOfVistor.size(); i++){
            JSONObject visitor = getVisitor(i).viewVisitorInfo();
            visitor.put("vid", getVisitor(i).getVid());
            visitorsInfo.put(visitor);
        }
        return visitorsInfo;
    }

    public boolean checkIfVisitorVisitedPark(int pid, int vid) {
        boolean visited = false;
        for(int i = 0; i < this.listOfOrders.size(); i++){
            if(listOfOrders.get(i).getVid() == vid && listOfOrders.get(i).getPid() == pid){
                visited = true;
            }
        }
        return visited;
    }

    public JSONObject viewSpecificVistors(int vid, JSONArray commentInfo) {
        JSONObject visitorInfo = new JSONObject();
        int idx = returnVistorIndex(vid);
        if(idx != -1) {
            visitorInfo.put("vid", vid);
            JSONObject name_email = getVisitor(idx).viewVisitorInfo();
            visitorInfo.put("visitor", name_email);
            JSONArray orderInfo = viewOrdersForVisitor(vid);
            visitorInfo.put("orders", orderInfo);
            JSONArray noteInfo = commentInfo;
            visitorInfo.put("notes", noteInfo);
            return visitorInfo;
        }
        return visitorInfo;
    }

    public JSONArray viewOrdersForVisitor(int vid) {
        JSONArray visitorsOrders = new JSONArray();
        for(int i = 0; i < this.listOfOrders.size(); i++){
            if(getOrder(i).getVid() == vid){
             JSONObject orderDetails = getOrder(i).viewOrder();
             orderDetails.remove("amount");
             orderDetails.remove("type");
             visitorsOrders.put(orderDetails);
            }
        }
        return visitorsOrders;
    }

    public JSONArray searchWithKey(String key) {
        JSONArray ordersWithKeys = new JSONArray();
        for(int i=0; i < this.listOfOrders.size(); i++){
            if(getOrder(i).searchKey(key)){
                ordersWithKeys.put(getOrder(i).viewOrder());
            }
        }
        return ordersWithKeys;
    }

    public JSONArray searchWithKeyVisitor(String key) {
        JSONArray visitorsWithKey = new JSONArray();
        JSONArray allVisitors = viewVisitors();
        for(int i =0; i < allVisitors.length(); i++){
            if(allVisitors.get(i).toString().contains(key)){
                visitorsWithKey.put(allVisitors.get(i));
            }
        }
        return visitorsWithKey;
    }
}
