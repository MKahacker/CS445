package ParkPaySystem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ParkInteractor {

    List<Park> parks;

    public ParkInteractor(List<Park> parks){
        this.parks = parks;
    }

    public int createAPark(String name, String region, String phone, String web, String address,
                           double lat, double lng, Payment[] parkPayment){
        Park newPark = new Park(-1, name, region, address, phone,web, new Geolocation(lat, lng), parkPayment);
        int pid = createPark(newPark);
        return pid;
    }

    public int createPark(Park newPark){
        int parkId = parks.size() + 100;
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

    public JSONArray getAllParksInfo(){
        Park parkAtIndex;
        JSONArray parksInfo = new JSONArray();

        for (int i = 0; i < parks.size(); i++){
            parkAtIndex = parks.get(i);
            parksInfo.put(parkAtIndex.viewInformation());
        }

        return  parksInfo;
    }

    public JSONObject getSpecificParkInfo(int id){
        Park parkReturned;
        JSONObject choosenPark = new JSONObject();
        if((parkReturned = getSpecificPark(id)) != null){
            choosenPark = parkReturned.viewInformation();
        }
        return choosenPark;
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
}
