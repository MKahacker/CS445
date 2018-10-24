package ParkPaySystem;

import java.util.List;

public class ParkInteractor {

    List<Park> parks;

    public ParkInteractor(List<Park> parks){
        this.parks = parks;
    }

    public void createPark(Park newPark){
        this.parks.add(newPark);
    }

    public String getAllParksInfo(){
        Park parkAtIndex;
        String parksInfo = "";

        for (int i = 0; i < parks.size(); i++){
            parkAtIndex = parks.get(i);
            parksInfo += parkAtIndex.viewInformation();
        }

        return  parksInfo;
    }

    public String getSpecificParkInfo(int id){
        Park parkAtIndex;
        Park choosenPark;
        String result = "";
        for (int i = 0; i < parks.size(); i++){
            parkAtIndex = parks.get(i);
            if(parkAtIndex.getParkId() == id){
                result = parkAtIndex.viewInformation();
                break;
            }
        }
        return result;
    }

}
