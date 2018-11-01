package ParkPaySystem;

import org.json.JSONArray;


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

    public int createNewOrder(int pid, double amount, Vehicle vehicleInfo, PaymentInfo paymentInfo, Date orderDate, String name, String email){
        int oid = listOfOrders.size() + 1;
        int vid = listOfVistor.size() + 1;

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
}
