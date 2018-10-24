package ParkPaySystem.test;

import ParkPaySystem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParkInteractorTest {
    private List<Park> list;
    private int size;

    @BeforeEach
    public void initTest(){
        Park someParks;
        String name = "Park#";
        String location = "123 Park Lane";
        Geolocation geo = new Geolocation(68.00, 80.00);
        Payment[] parkPayment = new Payment[3];
        parkPayment[0] = new MotorCycleFee(5.00, 8.00);
        parkPayment[1] = new CarFee(3.00, 10.00);
        parkPayment[2] = new RVFee(10.00, 11.00);

        size = 5;
        list = new ArrayList<Park>(size);
        for(int i = 0; i < size; i++){
            someParks = new Park (i, name + Integer.toString(i),location, "www.park.com", geo, parkPayment);
            list.add (someParks);
        }
    }

    @Test
    public void testReturnInformationProperly(){
        ParkInteractor myParks;
        String result = "";

        for(int i = 0; i < size; i++){
            result += list.get(i).viewInformation();
        }
        myParks = new ParkInteractor(list);

        assertEquals(result, myParks.getAllParksInfo());
    }

/*    @Test
    public void testReturnSpecificParkIfThere(){
        ParkInteractor myParks;
        Park newPark = new Park (size+1, "Yosemite", 12.00, null, "123 Lane");
        list.add(newPark);

        myParks = new ParkInteractor(list);

        assertEquals(newPark.viewInformation(), myParks.getSpecificParkInfo(size+1));
    }*/

    @Test
    public void testReturnEmptyIfSpecificParkNotThere(){
        ParkInteractor myParks;
        myParks = new ParkInteractor(list);
        assertEquals("", myParks.getSpecificParkInfo(size+1));
    }

    @Test
    public void testCreatePark(){
        ParkInteractor myParks;

    }

}