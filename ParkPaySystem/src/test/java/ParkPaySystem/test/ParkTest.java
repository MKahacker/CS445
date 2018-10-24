package ParkPaySystem.test;

import ParkPaySystem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ParkTest {
    Park testPark;

    @BeforeEach
    public void initTests(){
        Geolocation parkGeo = new Geolocation(49.0, 56.0);
        Payment[] parkPayment = new Payment[3];
        parkPayment[0] = new MotorCycleFee(5.00, 8.00);
        parkPayment[1] = new CarFee(3.00, 10.00);
        parkPayment[2] = new RVFee(10.00, 11.00);

        String region = "Northwestern";
        String address = "123 Park Lane, Park City, IL 610101";
        String phone = "708-909-1454";
        String web = "www.yosemite.com";
        testPark = new Park(101, "Yosemite",region, address, phone,web, parkGeo, parkPayment);
    }

    @Test
    public void testViewInformationGivesTheRightInformation(){
        String expected = "{ \"location_info\": { \"name\": \"Yosemite\", \"region\": \"Northwestern\"," +
                " \"address\": \"123 Park Lane, Park City, IL 610101\", \"phone\": 708-909-1454, \"web\": "+
                "\"wwww.yosemite.com\", \"geo\": { \"lat\": 49.0, \"lng\": 56.0 }";

        assertEquals(expected, testPark.viewInformation());
    }

    /*
    @Test
    public void testViewInformationWithMultipleComments(){
        Comment[] comments = new Comment[3];
        Date timeStamp = new Date();
        for(int i = 0; i < 3; i++){
            comments[i] = new Comment("Jeff",timeStamp,"Lovely" + Integer.toString(i));
        }
        Park firstPark = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");

        assertEquals("Id = 101,\nName = Yosemite,\nFee = $12.00,\nComments = \n\tLovely0,\n\tLovely1,\n\tLovely2,\nLocation = 123 Something Ln\n", firstPark.viewInformation());
    }

    @Test
    public void testViewInformationWithZeroComments(){

        assertEquals("Id = 101,\nName = Yosemite,\nFee = $12.00,\nComments = \nLocation = 123 Something Ln\n", testPark.viewInformation());
    }

    @Test
    public void testCheckCommentsWhenCommentsAreSame(){
        Date timeStamp = new Date();
        Comment[] comments1 = new Comment[2];
        Comment[] comments2 = new Comment[2];
        Comment firstComment = new Comment("Jeff", timeStamp, "Lovely");
        Comment secondComment = new Comment ("Jeff", timeStamp, "Lovely");
        comments1[0] = comments2[0] = firstComment;
        comments1[1] = comments2[1] = secondComment;
        Park testPark = new Park(101, "Yosemite", 12.00, comments1, "123 Something ln");

        assertEquals(true, testPark.checkComments(comments1,comments2));
    }

    @Test
    public void testCheckCommentsWhenCommentsAreDifferent(){
        Date timeStamp = new Date();
        Comment[] comments1 = new Comment[2];
        Comment[] comments2 = new Comment[2];
        Comment firstComment = new Comment("Jeff", timeStamp, "Lovely");
        Comment secondComment = new Comment ("Jeff", timeStamp, "Lovely1");
        comments1[0] = firstComment;
        comments2[0] = secondComment;
        comments1[1] = secondComment;
        comments2[1] = firstComment;
        Park testPark = new Park(101, "Yosemite", 12.00, comments1, "123 Something ln");

        assertEquals (comments1[1].equals(comments2[1]), testPark.checkComments(comments1,comments2));
    }
    */
}