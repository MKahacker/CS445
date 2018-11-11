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
import Reporting.Reports;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    public JsonNode getAllParks(@RequestParam(value="key", defaultValue="") String key){
        JsonNode parks;
        try {
            if(key.equals("")) {
                parks = parksMapper.readTree(myParks.getAllParksInfo().toString());
            }else{
                parks = parksMapper.readTree(myParks.getParksKey(key).toString());
            }
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
    public JsonNode getOrders(@RequestParam(value="key", defaultValue="") String key){
        JsonNode orders;
        try {
            if(key.equals("")) {
                orders = parksMapper.readTree(myOrder.viewAllOrders().toString());
            }else{
                orders = parksMapper.readTree(myOrder.searchWithKey(key).toString());
            }
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
    public JsonNode getOneOrder(@PathVariable("id") int oid){
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
    public JsonNode getVisitors(@RequestParam(value="key", defaultValue="") String key){
        JsonNode visitors;
        try {
            if(key.equals("")) {
                visitors = parksMapper.readTree(myOrder.viewVisitors().toString());
            }else{
                visitors = parksMapper.readTree(myOrder.searchWithKeyVisitor(key).toString());
            }
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
            visitors = parksMapper.readTree(myOrder.viewSpecificVistors(vid, myComment.viewCommentsForVisitor(vid)).toString());
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

    @GetMapping("/reports")
    public String getReports(){
        return Reports.getReport();
    }

    @PostMapping("/parks")
    public ResponseEntity<JsonNode> createPark(@RequestBody JsonNode parkinfo){
        JsonNode parkId = null;
        try {
            String[] locationInfo = locationInfoString(parkinfo);
            double[] geoInfo = geoInfoArray(parkinfo);
            Payment[] parkPayment = paymentArrayBuilder(parkinfo);
            if(geoInfo == null){
                parkId = parksMapper.readTree("{\"type\":\"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\":\"Your request failed validation\",\"detail\":\"geo information is required but missing\",\"status\":400," +
                        "\"instance\":\"/parks\"}");
                return new ResponseEntity<JsonNode>(parkId, HttpStatus.BAD_REQUEST);
            }
            if(locationInfo == null){
                parkId = parksMapper.readTree("{\"type\":\"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\":\"Your request failed validation\",\"detail\":\"location information is required but missing\",\"status\":400," +
                        "\"instance\":\"/parks\"}");
                return new ResponseEntity<JsonNode>(parkId, HttpStatus.BAD_REQUEST);
            }
            if(parkPayment == null){
                parkId = parksMapper.readTree("{\"type\":\"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\":\"Your request failed validation\",\"detail\":\"park payment information is required but missing\",\"status\":400," +
                        "\"instance\":\"/parks\"}");
                return new ResponseEntity<JsonNode>(parkId, HttpStatus.BAD_REQUEST);
            }
            int pid = myParks.createAPark(locationInfo[0],locationInfo[1],locationInfo[2],locationInfo[3],locationInfo[4],geoInfo[0],geoInfo[1],parkPayment);
            String json = "{\"pid\":\""+pid+"\"}";
            parkId = parksMapper.readTree(json);
            String uri =  "/parks/"+Integer.toString(pid);;
            return ResponseEntity.created(new URI(uri)).body(parkId);

        } catch(NullPointerException e){
            return new ResponseEntity<JsonNode>(parkId, HttpStatus.BAD_REQUEST);
        }catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        return new ResponseEntity<JsonNode>(parkId, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/parks/{pid}/notes")
    public ResponseEntity<JsonNode> createNote(@PathVariable("pid") int pid, @RequestBody JsonNode noteInfo){
        JsonNode noteId = null;
        if(!(noteInfo.has("vid"))) {
            return new ResponseEntity<JsonNode>(noteId, HttpStatus.BAD_REQUEST);
        }
        if(!(noteInfo.has("title"))) {
            return new ResponseEntity<JsonNode>(noteId, HttpStatus.BAD_REQUEST);
        }
        if(!(noteInfo.has("text"))) {
            return new ResponseEntity<JsonNode>(noteId, HttpStatus.BAD_REQUEST);
        }
        try {
        int vid = noteInfo.get("vid").asInt();
        if(!(myOrder.checkIfVisitorVisitedPark(pid, vid))){
            String json ="{" +
                    "\"type\": \"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                    "\"title\": \"Your request data didn't pass validation\"," +
                    "\"detail\": \"You may not post a note to a park unless you paid for admission at that park\"," +
                    "\"status\": 400," +
                    "\"instance\": \"/parks/" + pid + "\"" +
                    "}";
            noteId = parksMapper.readTree(json);
            return new ResponseEntity<JsonNode>(noteId, HttpStatus.BAD_REQUEST);
        }
        String title = noteInfo.get("title").asText();
        String body = noteInfo.get("text").asText();
        int nid = myComment.createNewComment(pid, vid, new Date(), title, body);
        String json= "{\"nid\":\"" + nid+"\"}";

            noteId = parksMapper.readTree(json);
            return new ResponseEntity<JsonNode>(noteId, HttpStatus.CREATED);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<JsonNode>(noteId, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/orders")
    public ResponseEntity<JsonNode> createOrder(@RequestBody JsonNode orderInfo){
        JsonNode orderId = null;
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
        String json = "{\"oid\":\"" + oid+"\"}";
        try {
            orderId = parksMapper.readTree(json);
            String uri = "/orders/"+Integer.toString(oid);
            return ResponseEntity.created(new URI(uri)).body(orderId);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        return new ResponseEntity<JsonNode>(orderId, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/parks/{id}")
    public ResponseEntity<Void> updatePark(@PathVariable("id") int pid, @RequestBody JsonNode parkinfo){
        if(myParks.getIndexOfPark(pid) != -1) {
            String[] locationInfo = locationInfoString(parkinfo);
            double[] geoInfo = geoInfoArray(parkinfo);
            Payment[] parkPayment = paymentArrayBuilder(parkinfo);
            myParks.updateAPark(locationInfo[0],locationInfo[1],locationInfo[2],locationInfo[3],locationInfo[4],geoInfo[0],geoInfo[1],parkPayment,pid);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/notes/{nid}")
    public ResponseEntity<Void> updateNotes(@PathVariable("nid") int nid, @RequestBody JsonNode noteInfo){
        if(myComment.returnIndexOfComment(nid) != -1){
            int vid = noteInfo.get("vid").asInt();
            String title = noteInfo.get("title").asText();
            String body = noteInfo.get("text").asText();
            myComment.updateComment(nid, vid, title,body);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/parks/{id}")
    public ResponseEntity<Void> deletePark(@PathVariable("id") int pid){
        if(myParks.getIndexOfPark(pid) != -1){
            myParks.deletePark(pid);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }


    public String[] locationInfoString(JsonNode parkinfo){
        String[] locationInfo = new String[5];
        if(parkinfo.has("location_info")) {
            if(parkinfo.path("location_info").path("name") != null &&
                    parkinfo.path("location_info").path("web") != null &&
                    parkinfo.path("location_info").path("web") != null &&
                    parkinfo.path("location_info").path("address") != null) {

                locationInfo[0] = parkinfo.path("location_info").path("name").asText();
                locationInfo[1] = parkinfo.path("location_info").path("region").asText();
                locationInfo[2] = parkinfo.path("location_info").path("phone").asText();
                locationInfo[3] = parkinfo.path("location_info").path("web").asText();
                locationInfo[4] = parkinfo.path("location_info").path("address").asText();

                if(locationInfo[0] == ""||locationInfo[3] == ""||locationInfo[4] == "")
                    return null;

                return locationInfo;
            }
        }
        return null;
    }

    public double[] geoInfoArray(JsonNode parkinfo){
        double[] geoInfo = new double[2];
        if(parkinfo.path("location_info").path("geo").path("lat") != null && parkinfo.path("location_info").path("geo").path("lng") != null) {
            geoInfo[0] = parkinfo.path("location_info").path("geo").path("lat").asDouble();
            geoInfo[1] = parkinfo.path("location_info").path("geo").path("lng").asDouble();
            if(geoInfo[0] == 0 || geoInfo[1] == 0)
                return null;
            return geoInfo;
        }
        return null;
    }

    public Payment[] paymentArrayBuilder(JsonNode parkinfo){
        Payment[] parkPayment = new Payment[3];
        try {
            JsonNode motorcycleFee = parkinfo.path("payment_info").path("motorcycle");
            JsonNode RVFee = parkinfo.path("payment_info").path("rv");
            JsonNode carFee = parkinfo.path("payment_info").path("car");
            if(motorcycleFee.get(0).asDouble() < 0 || motorcycleFee.get(1).asDouble() < 0 ||
            RVFee.get(0).asDouble() < 0 || RVFee.get(1).asDouble() < 0 ||
            carFee.get(0).asDouble() < 0 || carFee.get(1).asDouble() < 0) {
                return null;
            }
            parkPayment[Payment.paymentType("car")] = new CarFee(carFee.get(0).asDouble(), carFee.get(1).asDouble());
            parkPayment[Payment.paymentType("motorcycle")] = new MotorCycleFee(motorcycleFee.get(0).asDouble(), motorcycleFee.get(1).asDouble());
            parkPayment[Payment.paymentType("rv")] = new RVFee(RVFee.get(0).asDouble(), RVFee.get(1).asDouble());

            return parkPayment;
        }catch (NullPointerException e){
            return null;
        }
    }

}
