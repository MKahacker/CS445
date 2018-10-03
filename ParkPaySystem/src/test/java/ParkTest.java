package test.java;

import main.java.ParkPaySystem.Park;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkTest {

    @Test
    public void testConstructor(){
        String [] comments = new String[5];
        Park park1 = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");

        assertEquals(park1, park1);
    }

    @Test
    public void testEqualsTrue(){
        String [] comments = new String[5];
        Park park1 = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");
        Park park2 = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");
        Boolean results = park1.equals(park2);

        assertEquals(results, Boolean.TRUE);
    }

    @Test
    public void testEqualsFalse(){
        String [] comments = new String[5];
        Park park1 = new Park(101,"Yosemite", 12.0, comments, "123 Something Ln");
        Park park2 = new Park(102, "Cherokee", 12.0, comments, "123 Something Ln");
        Boolean results = park1.equals(park2);

        assertEquals(results, Boolean.FALSE);
    }

    @Test
    public void testViewInformationGivesTheRightInformation(){
        String[] comments = new String[1];
        comments[0] = "Lovely";
        Park firstPark = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");

        assertEquals("Id = 101,\nName = Yosemite,\nFee = $12.00,\nComments = \n\tLovely,\nLocation = 123 Something Ln", firstPark.viewInformation());
    }

    @Test
    public void testViewInformationWithMultipleComments(){
        String[] comments = new String[3];
        comments[0] = "Lovely";
        comments[1] = "Beautiful!";
        comments[2] = "Was amazing";
        Park firstPark = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");

        assertEquals("Id = 101,\nName = Yosemite,\nFee = $12.00,\nComments = \n\tLovely,\n\tBeautiful!,\n\tWas amazing,\nLocation = 123 Something Ln", firstPark.viewInformation());
    }

    @Test
    public void testViewInformationWithZeroComments(){
        String[] comments = new String[0];
        Park firstPark = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");

        assertEquals("Id = 101,\nName = Yosemite,\nFee = $12.00,\nComments = \nLocation = 123 Something Ln", firstPark.viewInformation());
    }

}