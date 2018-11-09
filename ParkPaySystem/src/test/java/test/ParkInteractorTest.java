package test;

import Fee.CarFee;
import Park.Geolocation;
import Fee.MotorCycleFee;
import Park.Park;
import Park.ParkInteractor;
import Fee.Payment;
import Fee.RVFee;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParkInteractorTest {
    private List<Park> list;
    private int size;
    Geolocation geo;
    Payment[] parkPayment;

    @BeforeEach
    public void initTest(){
        Park someParks;
        String name = "Park#";
        String location = "123 Park Lane";
        geo = new Geolocation(68.00, 80.00);
        parkPayment = assemblePayment();
        size = 5;
        list = new ArrayList<Park>(size);
        for(int i = 0; i < size; i++){
            someParks = new Park (i, name + Integer.toString(i),location, "www.park.com", geo, parkPayment);
            list.add (someParks);
        }
    }

    public Payment[] assemblePayment(){
        Payment[] returnPayment = new Payment[3];

        returnPayment[Payment.paymentType("car")] = new CarFee(3.00, 10.00);
        returnPayment[Payment.paymentType("RV")] = new RVFee(10.00, 11.00);
        returnPayment[Payment.paymentType("motorcycle")] = new MotorCycleFee(5.00, 8.00);

        return returnPayment;
    }

    @Test
    public void testReturnInformationWithNothing(){
        ParkInteractor myParks = new ParkInteractor(new ArrayList<Park>());
        assertEquals("[]", myParks.getAllParksInfo().toString());
    }

    @Test
    public void testReturnInformationProperly(){
        ParkInteractor myParks;
        JSONArray result = new JSONArray();

        for(int i = 0; i < size; i++){
            JSONObject park = list.get(i).viewInformation();
            result.put(park);
        }
        myParks = new ParkInteractor(list);

        assertEquals(result.toString(), myParks.getAllParksInfo().toString());
    }

    @Test
    public void testReturnSpecificParkInformationIfThere(){
        ParkInteractor myParks;
        Payment[] fee = assemblePayment();

        Park newPark = new Park (size+1, "Yosemite", "Northwest", "www.park.com", new Geolocation(59.00,56.7), fee);
        list.add(newPark);

        myParks = new ParkInteractor(list);

        JSONObject specificPark = newPark.viewInformation();
        JSONArray motorCycleFee = new JSONArray();
        JSONArray carFee = new JSONArray();
        JSONArray rvFee = new JSONArray();
        motorCycleFee.put(newPark.inStateFee(Payment.paymentType("motorcycle")));
        motorCycleFee.put(newPark.outStateFee(Payment.paymentType("motorcycle")));
        JSONObject paymentArray = new JSONObject();
        paymentArray.put("motorcycle", motorCycleFee);
        carFee.put(newPark.inStateFee(Payment.paymentType("car")));
        carFee.put(newPark.outStateFee(Payment.paymentType("car")));
        paymentArray.put("car", carFee);
        rvFee.put(newPark.inStateFee(Payment.paymentType("rv")));
        rvFee.put(newPark.outStateFee(Payment.paymentType("rv")));
        paymentArray.put("rv",rvFee);
        specificPark.put("payment_info", paymentArray);
        assertEquals(specificPark.toString(), myParks.getSpecificParkInfo(size+1).toString());
    }

    @Test
    public void testReturnEmptyIfSpecificParkNotThere(){
        ParkInteractor myParks= new ParkInteractor(list);
        assertEquals("{}", myParks.getSpecificParkInfo(size+1).toString());
    }

    @Test
    public void testCreatePark(){
        ParkInteractor myParks = new ParkInteractor(list);
        Park newPark = new Park(-1, "White Moon", "1234 Michigan Ave", "www.whitemoon.com", geo, parkPayment);
        int pid = myParks.createPark(newPark);
        JSONObject parkInfo = myParks.getSpecificParkInfo(pid);
        parkInfo.remove("payment_info");
        assertEquals(newPark.viewInformation().toString(), parkInfo.toString());
    }

    @Test
    public void testCreateParkWithAllParametersPassed(){
        ParkInteractor myParks = new ParkInteractor(list);
        Park newPark = new Park(-1, "White Moon", "1234 Michigan Ave", "www.whitemoon.com", geo, parkPayment);
        int pid = myParks.createAPark("White Moon", "", "","www.whitemoon.com",
                "1234 Michigan Ave", geo.getLat(), geo.getLng(), parkPayment);
        newPark.setParkId(pid);
        JSONObject parkInfo = myParks.getSpecificParkInfo(pid);
        parkInfo.remove("payment_info");
        assertEquals(newPark.viewInformation().toString(), parkInfo.toString());
    }

    @Test
    public void testGetSpecificParkIfExists(){
        ParkInteractor myParks;
        Payment[] fee = assemblePayment();

        Park newPark = new Park (709, "Yosemite", "Northwest", "www.park.com", new Geolocation(59.00,56.7), fee);
        list.add(newPark);

        myParks = new ParkInteractor(list);

        assertEquals(newPark, myParks.getSpecificPark(709));
    }

    @Test
    public void testReturnNullIfSpecificParkNotExists(){
        ParkInteractor myParks;
        myParks = new ParkInteractor(list);
        assertEquals(null, myParks.getSpecificPark(size+2) );
    }

    @Test
    public void testIfParkUpdated(){
        ParkInteractor myParks = new ParkInteractor(list);

        Park newPark = new Park(size+1, "Yosemite", "Northwest", "www.park.com", geo, parkPayment);
        Park updatePark = new Park(size+1, "Yosemite", "Southwest", "www.park2.com", geo, parkPayment);

        int pid = myParks.createPark(newPark);
        myParks.updatePark(updatePark, pid);

        JSONObject parkInfo = myParks.getSpecificParkInfo(pid);
        parkInfo.remove("payment_info");

        assertEquals(updatePark.viewInformation().toString(), parkInfo.toString());
    }

    @Test
    public void testIfParkUpdatedWithAllParamenters(){
        ParkInteractor myParks = new ParkInteractor(list);

        Park newPark = new Park(size+1, "Yosemite", "Northwest", "www.park.com", geo, parkPayment);
        Park updatePark = new Park(size+1, "Yosemite", "Southwest", "www.park2.com", geo, parkPayment);

        int pid = myParks.createPark(newPark);
        myParks.updateAPark("Yosemite","","",
                "www.park2.com","Southwest",geo.getLat(),geo.getLng(), parkPayment, pid);

        updatePark.setParkId(pid);
        JSONObject parkInfo = myParks.getSpecificParkInfo(pid);
        parkInfo.remove("payment_info");

        assertEquals(updatePark.viewInformation().toString(), parkInfo.toString());
    }

    @Test
    public void testIfDeleteWorks(){
        ParkInteractor myParks = new ParkInteractor(list);
        myParks.deletePark(size-1);

        assertEquals(null, myParks.getSpecificPark(size-1));
    }

    @Test
    public void testParkFeeGetter(){
        ParkInteractor myParks = new ParkInteractor(list);
        assertEquals(3.00, myParks.getParkFee(size-1, "car", "IL"));
        assertEquals(10.00, myParks.getParkFee(size-1, "car", "IN"));

        assertEquals(5.00, myParks.getParkFee(size-1, "motorcycle", "IL"));
        assertEquals(8.00, myParks.getParkFee(size-1, "motorcycle", "OK"));

        assertEquals(10.00, myParks.getParkFee(size-1, "RV", "Il"));
        assertEquals(11.00, myParks.getParkFee(size-1, "RV", "WY"));

    }

    @Test
    public void testParksKey(){
        ParkInteractor myParks = new ParkInteractor(list);
        assertEquals("[]", myParks.getParksKey("South").toString());
        assertEquals(myParks.getAllParksInfo().toString(), myParks.getParksKey("pid").toString());
    }

}