package test.java;

import main.java.ParkPaySystem.Park;
import main.java.ParkPaySystem.ParkInteractor;
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
        size = 5;
        list = new ArrayList<Park>(size);
        for(int i = 0; i < size; i++){
            someParks = new Park (i, name + Integer.toString(i), 12.00*(i+1), null, location);
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

    @Test
    public void testReturnSpecificParkIfThere(){
        ParkInteractor myParks;
        Park newPark = new Park (size+1, "Yosemite", 12.00, null, "123 Lane");
        list.add(newPark);

        myParks = new ParkInteractor(list);

        assertEquals(newPark.viewInformation(), myParks.getSpecificParkInfo(size+1));
    }

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