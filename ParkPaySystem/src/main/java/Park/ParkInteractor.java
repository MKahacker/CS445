package Park;

import Fee.Payment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkInteractor {

    List<Park> parks;
    private static AtomicInteger idGenerator = new AtomicInteger(100);

    public ParkInteractor(List<Park> parks){
        this.parks = parks;
    }

    public int createAPark(String name, String region, String phone, String web, String address,
                           double lat, double lng, Payment[] parkPayment){
        Park newPark = new Park(-1, name, region, address, phone,web, new Geolocation(lat, lng), parkPayment);
        return createPark(newPark);
    }

    public int createPark(Park newPark){
        int parkId = idGenerator.getAndIncrement();
        newPark.setParkId(parkId);
        this.parks.add(newPark);
        return parkId;
    }

    public void updateAPark(String name, String region, String phone, String web, String address,
                           double lat, double lng, Payment[] parkPayment, int id){
        Park updatedPark = new Park(id, name, region, address, phone,web, new Geolocation(lat, lng), parkPayment);
        updatePark(updatedPark, id);
    }

    public void updatePark(Park updatedPark, int id){
        int i = getIndexOfPark(id);
        updatedPark.setParkId(id);
        this.parks.set(i, updatedPark);
    }

    public void deletePark(int id){
        int indexToDelete = getIndexOfPark(id);
        this.parks.remove(indexToDelete);
    }

    public int getIndexOfPark(int id){
        int idx = -1;
        for(int i= 0; i < this.parks.size(); i++){
            if(this.parks.get(i).getParkId() == id){
                idx = i;
                return idx;
            }
        }
        return idx;
    }

    public JSONArray getAllParksInfo(String key){
        Park parkAtIndex;
        JSONArray parksInfo = new JSONArray();
        for (int i = 0; i < parks.size(); i++){
            parkAtIndex = parks.get(i);
            String parkString = parkAtIndex.viewInformation().toString().toLowerCase();
            if(parkString.contains(key.toLowerCase())){
                parksInfo.put(parkAtIndex.viewInformation());
            }

        }
        return  parksInfo;
    }

    public JSONObject getSpecificParkInfo(int id){
        Park parkReturned;
        JSONObject choosenPark = new JSONObject();

        if((parkReturned = getSpecificPark(id)) != null){
            JSONObject paymentInfo = buildPaymentInfo(parkReturned);
            choosenPark = parkReturned.viewInformation();
            choosenPark.put("payment_info", paymentInfo);
        }
        return choosenPark;
    }

    public JSONObject buildPaymentInfo(Park parkReturned){
        JSONObject paymentInfo = new JSONObject();
        JSONArray motorcyclefee = new JSONArray();
        JSONArray rvfee = new JSONArray();
        JSONArray carFee = new JSONArray();

        motorcyclefee.put(parkReturned.inStateFee(Payment.paymentType("motorcycle")));
        motorcyclefee.put(parkReturned.outStateFee(Payment.paymentType("motorcycle")));
        paymentInfo.put("motorcycle", motorcyclefee);

        rvfee.put(parkReturned.inStateFee(Payment.paymentType("rv")));
        rvfee.put(parkReturned.outStateFee(Payment.paymentType("rv")));
        paymentInfo.put("rv", rvfee);

        carFee.put(parkReturned.inStateFee(Payment.paymentType("car")));
        carFee.put(parkReturned.outStateFee(Payment.paymentType("car")));
        paymentInfo.put("car", carFee);

        return paymentInfo;
    }

    public Park getSpecificPark(int id){
        Park choosenPark = null;
        for (int i = 0; i < parks.size(); i++){
            if(parks.get(i).getParkId() == id){
                choosenPark = parks.get(i);
                break;
            }
        }
        return choosenPark;
    }

    public double getParkFee(int pid, String type, String state) {
        double fee = 0;
        String uppercaseState = state.toUpperCase();
        InterfacePark park = getSpecificPark(pid);
        if(uppercaseState.equals("IL")) {
                fee = park.inStateFee(Payment.paymentType(type));
        }else{
                fee = park.outStateFee(Payment.paymentType(type));
        }

        return fee;
    }


}
