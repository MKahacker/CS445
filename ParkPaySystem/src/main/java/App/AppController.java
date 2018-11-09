package App;

import Comment.Comment;
import Comment.CommentManager;
import Fee.CarFee;
import Fee.MotorCycleFee;
import Fee.Payment;
import Fee.RVFee;
import Order.Order;
import Order.OrderManager;
import Park.Park;
import Park.ParkInteractor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/parkpay")
public class AppController {
    private List<Park> listPark = new ArrayList<Park>();
    private ParkInteractor myParks = new ParkInteractor(listPark);
    private CommentManager myComment = new CommentManager(new ArrayList<Comment>());
    private OrderManager myOrder = new OrderManager(new ArrayList<Order>());
    private ObjectMapper parksMapper = new ObjectMapper();

    @GetMapping("/parks")
    public JsonNode getAllParks(){
        JsonNode parks;
        try {
            parks = parksMapper.readTree(myParks.getAllParksInfo().toString());
            return parks;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/parks/{id}")
    public JsonNode getAPark(@PathVariable("id") int pid){
        JsonNode apark;
        try {
            apark = parksMapper.readTree(myParks.getSpecificParkInfo(pid).toString());
            return apark;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/parks/{pid}/notes")
    public JsonNode viewNotesAssociatedWithPark(@PathVariable("pid") int id){
        JsonNode notes;
        try {
            notes = parksMapper.readTree(myComment.viewCommentsForPark(id).toString());
            return notes;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping({"/parks/{pid}/notes/{nid}", "/notes/{nid}"})
    public JsonNode viewNote(@PathVariable("nid") int id){
        JsonNode note;
        try {
            note = parksMapper.readTree(myComment.viewSpecificComment(id).toString());
            return note;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/orders")
    public JsonNode getOrders(){
        JsonNode orders;
        try {
            orders = parksMapper.readTree(myOrder.viewAllOrders().toString());
            return orders;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/orders/{id}")
    public JsonNode getOneOrder(@PathVariable int oid){
        JsonNode orders;
        try {
            orders = parksMapper.readTree(myOrder.viewSpecificOrder(oid).toString());
            return orders;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/visitors")
    public JsonNode getVisitors(){
        JsonNode visitors;
        try {
            visitors = parksMapper.readTree(myOrder.viewVisitors().toString());
            return visitors;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/visitors/{id}")
    public JsonNode getAVisitor(@PathVariable("id") int vid){
        JsonNode visitors;
        try {
            visitors = parksMapper.readTree(myOrder.viewVisitors().toString());
            return visitors;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/parks")
    public ResponseEntity<JsonNode> createPark(@RequestBody JsonNode parkinfo){
        JsonNode parkId = null;
        try {
            String[] locationInfo = locationInfoString(parkinfo);
            double[] geoInfo = geoInfoArray(parkinfo);
            Payment[] parkPayment = paymentArrayBuilder(parkinfo);
            int pid = myParks.createAPark(locationInfo[0],locationInfo[1],locationInfo[2],locationInfo[3],locationInfo[4],geoInfo[0],geoInfo[1],parkPayment);
            String json = "{\"pid\":"+pid+"}";
            try {
                parkId = parksMapper.readTree(json);
                return new ResponseEntity<JsonNode>(parkId, HttpStatus.ACCEPTED);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch(NullPointerException e){
            return new ResponseEntity<JsonNode>(parkId, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<JsonNode>(parkId, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/parks/{pid}/notes")
    public JsonNode createNote(@PathVariable("pid") int pid, @RequestBody JsonNode noteInfo){
        int vid = noteInfo.get("vid").asInt();
        String title = noteInfo.get("title").asText();
        String body = noteInfo.get("text").asText();
        int nid = myComment.createNewComment(pid, vid, new Date(), title, body);
        String json= "{\"nid\":" + nid+"}";
        JsonNode noteId;
        try {
            noteId = parksMapper.readTree(json);
            return noteId;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/orders")
    public JsonNode createOrder(@RequestBody JsonNode orderInfo){
        JsonNode orderId;
        int pid = orderInfo.get("pid").asInt();
        String state = orderInfo.path("vehicle").path("state").asText();
        String plate = orderInfo.path("vehicle").path("plate").asText();
        String type = orderInfo.path("vehicle").path("type").asText();
        String name = orderInfo.path("visitor").path("name").asText();
        String email = orderInfo.path("visitor").path("email").asText();
        String card = orderInfo.path("visitor").path("payment_info").path("card").asText();
        String cardName = orderInfo.path("visitor").path("payment_info").path("name_on_card").asText();
        String expirationDate = orderInfo.path("visitor").path("payment_info").path("expiration_date").asText();
        int zip = orderInfo.path("visitor").path("payment_info").path("zip").asInt();
        double amount = myParks.getParkFee(pid, type, state);
        Vehicle vehicleInfo = new Vehicle(state, plate, type);
        PaymentInfo paymentInfo = new PaymentInfo(zip, card, cardName, expirationDate);
        int oid = myOrder.createNewOrder(pid,amount, vehicleInfo,paymentInfo,new Date(), name, email);
        String json = "{\"oid\":" + oid+"}";
        try {
            orderId = parksMapper.readTree(json);
            return orderId;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/parks/{id}")
    public void updatePark(@PathVariable("id") int pid, @RequestBody JsonNode parkinfo){
        if(myParks.getIndexOfPark(pid) != -1) {
            String[] locationInfo = locationInfoString(parkinfo);
            double[] geoInfo = geoInfoArray(parkinfo);
            Payment[] parkPayment = paymentArrayBuilder(parkinfo);

            myParks.updateAPark(locationInfo[0],locationInfo[1],locationInfo[2],locationInfo[3],locationInfo[4],geoInfo[0],geoInfo[1],parkPayment,pid);
        }
    }

    @PutMapping("/notes/{nid}")
    public void updateNotes(@PathVariable("nid") int nid, @RequestBody JsonNode noteInfo){
        if(myComment.returnIndexOfComment(nid) != -1){
            int vid = noteInfo.get("vid").asInt();
            String title = noteInfo.get("title").asText();
            String body = noteInfo.get("text").asText();
            myComment.updateComment(nid, vid, title,body);
        }
    }

    @DeleteMapping("/parks/{id}")
    public void deletePark(@PathVariable("id") int pid){
        if(myParks.getIndexOfPark(pid) != -1){
            myParks.deletePark(pid);
        }
    }


    public String[] locationInfoString(JsonNode parkinfo){
        String[] locationInfo = new String[5];
        locationInfo[0] = parkinfo.path("location_info").path("name").asText();
        locationInfo[1] = parkinfo.path("location_info").path("region").asText();
        locationInfo[2] = parkinfo.path("location_info").path("phone").asText();
        locationInfo[3] = parkinfo.path("location_info").path("web").asText();
        locationInfo[4] = parkinfo.path("location_info").path("address").asText();

        return locationInfo;
    }

    public double[] geoInfoArray(JsonNode parkinfo){
        double[] geoInfo = new double[2];
        geoInfo[0] = parkinfo.path("location_info").path("geo").path("lat").asDouble();
        geoInfo[1] = parkinfo.path("location_info").path("geo").path("lng").asDouble();

        return geoInfo;
    }

    public Payment[] paymentArrayBuilder(JsonNode parkinfo){
        Payment[] parkPayment = new Payment[3];

        JsonNode motorcycleFee = parkinfo.path("payment_info").path("motorcycle");
        JsonNode RVFee = parkinfo.path("payment_info").path("rv");
        JsonNode carFee = parkinfo.path("payment_info").path("car");

        parkPayment[Payment.paymentType("car")] = new CarFee(carFee.get(0).asDouble(), carFee.get(1).asDouble());
        parkPayment[Payment.paymentType("motorcycle")] = new MotorCycleFee(motorcycleFee.get(0).asDouble(), motorcycleFee.get(1).asDouble());
        parkPayment[Payment.paymentType("rv")] = new RVFee(RVFee.get(0).asDouble(), RVFee.get(1).asDouble());

        return parkPayment;
    }

}
