package ParkPaySystem;

import org.json.JSONArray;


import java.util.List;

public class OrderManager {
    private List<Order> listOfOrders;

    public OrderManager(List<Order> listOfOrders) {
        this.listOfOrders = listOfOrders;
    }

    public JSONArray viewAllOrders() {
        JSONArray orders = new JSONArray();
        for(int i = 0; i < this.listOfOrders.size(); i++)
            orders.put(listOfOrders.get(i).viewOrder());
        return orders;
    }
}
