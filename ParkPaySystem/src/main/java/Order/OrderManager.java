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
        int vid = listOfVistor.size() + 100;

        AbstractVistor newVistor = new Vistor(vid, name, email);
        listOfVistor.add(newVistor);

        Order newOrder = new Order(oid, pid, vid, amount, vehicleInfo, paymentInfo, orderDate);
        listOfOrders.add(newOrder);

        return oid;
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
        JSONObject visitor = new JSONObject();
        for(int i = 0; i < this.listOfVistor.size(); i++){
            visitor = getVisitor(i).viewVisitorInfo();
            visitor.put("vid", getVisitor(i).getVid());
            visitorsInfo.put(visitor);
        }
        return visitorsInfo;
    }
}
