package test;

import Fee.CarFee;
import Fee.MotorCycleFee;
import Fee.Payment;
import Fee.RVFee;
import Park.Park;
import Park.Geolocation;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkTest {
    Park testPark;
    JSONObject expected1;
    JSONObject expected2;
    JSONObject expected3;

    @BeforeEach
    public void initTests(){
        Geolocation parkGeo = new Geolocation(49.2, 56.1);
        Payment[] parkPayment = buildPayment();

        String region = "Northwestern";
        String address = "123 Park Lane, Park City, IL 610101";
        String phone = "708-909-1454";
        String web = "www.yosemite.com";

        testPark = new Park(101, "Yosemite",region, address, phone,web, parkGeo, parkPayment);
        buildJSON(101, "Yosemite", region, address, phone, web, parkGeo);
    }

    public void buildJSON(int id, String name, String region, String address, String phone, String web, Geolocation geo){
        expected1 = new JSONObject();
        expected2 = new JSONObject();
        expected3 = new JSONObject();

        expected3.put("lat", geo.getLat());
        expected3.put("lng", geo.getLng());

        expected2.put("name", name);
        expected2.put("region", region);
        expected2.put("address", address);
        expected2.put("phone", phone);
        expected2.put("web", web);
        expected2.put("geo", expected3);

        expected1.put("pid", id);
        expected1.put("location_info", expected2);
    }

    public Payment[] buildPayment(){
        Payment[] newPayment = new Payment[3];

        newPayment[0] = new MotorCycleFee(5.00, 8.00);
        newPayment[1] = new CarFee(3.00, 10.00);
        newPayment[2] = new RVFee(10.00, 11.00);

        return newPayment;
    }

    @Test
    public void testGetGeo(){
        Geolocation myGeo = new Geolocation(49.2, 56.1);
        assertEquals(myGeo.getLat(), testPark.getGeo().getLat());
        assertEquals(myGeo.getLng(), testPark.getGeo().getLng());
    }

    @Test
    public void testViewInformationGivesTheRightInformation(){
       String expected = expected1.toString();
        String actual = testPark.viewInformation().toString();


        assertEquals(expected, actual);
    }

    @Test
    public void testParseLocationInfo(){
        String expected = expected2.toString();
        String actual = testPark.parseLocationInfo().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testParseGeoInfo(){
        String expected = expected3.toString();
        String actual = testPark.parseGeoInfo().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testInStateFeeWithACar(){
        assertEquals(3.00, testPark.inStateFee(Payment.paymentType("car")));
    }

    @Test
    public void testInStateFeeWithRV(){
        assertEquals(10.00, testPark.inStateFee(Payment.paymentType("rv")));
    }

    @Test
    public void testInStateFeeWithMotorcycle(){
        assertEquals(5.00, testPark.inStateFee(Payment.paymentType("motorcycle")));
    }

    @Test
    public void testInStateFeeWithIllegalInput(){
        assertEquals (-1, testPark.inStateFee(Payment.paymentType("plane")));
    }

    @Test
    public void testOutStateFeeWithCar(){
        assertEquals(10.00, testPark.outStateFee(Payment.paymentType("Car")));
    }

    @Test
    public void testOutStateFeeWithRV(){
        assertEquals(11.00, testPark.outStateFee(Payment.paymentType("RV")));
    }

    @Test
    public void testOutStateFeeWithMotorcycle(){
        assertEquals(8.00, testPark.outStateFee(Payment.paymentType("Motorcycle")));
    }

    @Test
    public void testOutStateFeeWithIllegalInput(){
        assertEquals(-1, testPark.outStateFee(Payment.paymentType("plane")));
    }
}