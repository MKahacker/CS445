package test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import main.java.ParkPaySystem.*;

class AdminTest {

    @Test
    public void testListParksWithNoParks(){
        Park[] parks = new Park[0];
        Admin parkAdmin = new Admin(parks);

        assertEquals("", parkAdmin.listParks());
    }

    @Test
    public void testListParksWithOnePark(){
        Park[] parks = new Park[1];
        Park onePark = new Park (101, "Yosemite", 12.00, new Comment[0], "123 Something Ln");
        parks[0] = onePark;
        Admin parkAdmin = new Admin(parks);

        assertEquals(onePark.viewInformation()+"\n", parkAdmin.listParks());
    }

    @Test
    public void testListParksWithManyParks(){
        Park[] parks = new Park[5];
        String testResults = "";
        for(int i = 0; i < 5; i++){
            parks[i] = new Park(101+i,  "Yosemite", 12.00, new Comment[0], "123 Something Ln");
            testResults = testResults + parks[i].viewInformation() + "\n";
        }
        Admin parkAdmin = new Admin(parks);

        assertEquals(testResults, parkAdmin.listParks());
    }

    @Test
    public void testCreateParkWhenNoOtherParksExist(){
        Park[] parks = new Park[0];
        Admin parkAdmin = new Admin(parks);
        Park testPark = new Park (101, "Yosemite", 12.00, new Comment[0], "123 Something Ln");
        parkAdmin.createPark("Yosemite", "123 Something Ln");

        assertEquals(testPark.viewInformation() + "\n", parkAdmin.listParks());
    }

}