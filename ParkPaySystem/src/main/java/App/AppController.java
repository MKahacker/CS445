package App;

import Comment.Comment;
import Comment.CommentManager;
import Fee.CarFee;
import Fee.MotorCycleFee;
import Fee.Payment;
import Fee.RVFee;
import Order.Order;
import Order.OrderManager;
import Order.PaymentInfo;
import Order.Vehicle;
import Park.Park;
import Park.ParkInteractor;
import Reporting.Reports;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    SimpleDateFormat requestDate = new SimpleDateFormat("yyyyMMdd");
    DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

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
    public ResponseEntity<JsonNode> getAPark(@PathVariable("id") int pid){
        JsonNode apark;
        try {
            if(myParks.getIndexOfPark(pid)==-1){
                return ResponseEntity.notFound().build();
            }else {
                apark = parksMapper.readTree(myParks.getSpecificParkInfo(pid).toString());
                return ResponseEntity.ok().body(apark);
            }
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
    public ResponseEntity<JsonNode> viewNotesAssociatedWithPark(@PathVariable("pid") int id){
        JsonNode notes;
        try {
            if(myParks.getIndexOfPark(id) == -1){
                return ResponseEntity.notFound().build();
            }else {
                notes = parksMapper.readTree(myComment.viewCommentsForPark(id).toString());
                return ResponseEntity.ok().body(notes);
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping({"/parks/{pid}/notes/{nid}"})
    public ResponseEntity<JsonNode> viewNote(@PathVariable("pid") int pid, @PathVariable("nid") int id){
        JsonNode note;
        try {
            if(myParks.getIndexOfPark(pid)==-1 || myComment.returnIndexOfComment(id) == -1){
                return ResponseEntity.notFound().build();
            }else if(!(myComment.checkIfAssociated(pid, id))){
                String errorMsg = "{\"type\": \"http://cs.iit.edu/~virgil/cs445/" +
                        "project/api/problems/data-validation\","+
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"The note is not associated with this park\","+
                        "\"status\": 400,"+
                        "\"instance\": \"/parks/"+pid+"/notes/"+id+"\"}";
                JsonNode errorReponse = parksMapper.readTree(errorMsg);
                return ResponseEntity.badRequest().body(errorReponse);
            }else{
                note = parksMapper.readTree(myComment.viewSpecificComment(id).toString());
                return ResponseEntity.ok().body(note);
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping({"/notes/{nid}"})
    public ResponseEntity<JsonNode> viewNote(@PathVariable("nid") int id){
        JsonNode note;
        try {
            if(myComment.returnIndexOfComment(id) == -1){
                return ResponseEntity.notFound().build();
            }else {
                note = parksMapper.readTree(myComment.viewSpecificComment(id).toString());
                return ResponseEntity.ok().body(note);
            }
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
    public ResponseEntity<JsonNode> getOneOrder(@PathVariable("id") int oid){
        JsonNode orders;
        try {
            if(myOrder.returnIndex(oid) == -1){
                return ResponseEntity.notFound().build();
            }else {
                orders = parksMapper.readTree(myOrder.viewSpecificOrder(oid).toString());
                return ResponseEntity.ok().body(orders);
            }
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
    public ResponseEntity<JsonNode> getAVisitor(@PathVariable("id") int vid){
        JsonNode visitors;
        try {
            if(myOrder.returnIndex(vid) == -1){
                return new ResponseEntity<JsonNode>(HttpStatus.NOT_FOUND);
            }else {
                visitors = parksMapper.readTree(myOrder.viewSpecificVistors(vid, myComment.viewCommentsForVisitor(vid)).toString());
                return ResponseEntity.ok().body(visitors);
            }
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
    public JsonNode getReports(){
        JsonNode reports = null;
        try{
           reports = parksMapper.readTree( Reports.getReport());
        }catch (IOException e){

        }
        return reports;
    }

    @GetMapping("/reports/567")
    public ResponseEntity<JsonNode> getAdmissionsReport(@RequestParam(value="start_date", defaultValue="") String startDate,
                                        @RequestParam(value="end_date", defaultValue="") String endDate){
        JsonNode admissionReport = null;
        JsonNode parseError = null;
        requestDate.setLenient(false);
        try {
            String parseErrorString = "{\"type\": \"http://cs.iit.edu/~virgil/cs445/" +
                    "project/api/problems/data-validation\","+
                    "\"title\": \"Your request data didn't pass validation\"," +
                    "\"detail\": \"Wrong date format try (yyyyMMdd)\","+
                    "\"status\": 400,"+
                    "\"instance\": \"/reports/567\"}";
            parseError = parksMapper.readTree(parseErrorString);

            if(startDate.equals("") && endDate.equals("")){
                admissionReport = parksMapper.readTree(Reports.getAdmissionReport(myParks.getAllParksInfo(),
                        myOrder.viewAllOrders(), startDate, endDate).toString());

                return ResponseEntity.ok().body(admissionReport);
            }
            if(startDate.equals("")){
                Date end_Date = requestDate.parse(endDate);
                endDate = myFormat.format(end_Date);

                admissionReport = parksMapper.readTree(Reports.getAdmissionReport(myParks.getAllParksInfo(),
                        myOrder.viewAllOrders(), startDate, endDate).toString());

                return ResponseEntity.ok().body(admissionReport);
            }
            if(endDate.equals("")){
                Date start_date = requestDate.parse(startDate);
                startDate = myFormat.format(start_date);

                admissionReport = parksMapper.readTree(Reports.getAdmissionReport(myParks.getAllParksInfo(),
                        myOrder.viewAllOrders(), startDate, endDate).toString());

                return ResponseEntity.ok().body(admissionReport);
            }

            Date start_Date = requestDate.parse(startDate);
            Date end_Date = requestDate.parse(endDate);
            if(start_Date.compareTo(end_Date)> 0){
                String json = "{\"type\": \"http://cs.iit.edu/~virgil/cs445/" +
                        "project/api/problems/data-validation\","+
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"Start date has to be before end Date\","+
                        "\"status\": 400,"+
                        "\"instance\": \"/reports/567\"}";
                admissionReport = parksMapper.readTree(json);
                return ResponseEntity.badRequest().body(admissionReport);
            }
            startDate = myFormat.format(start_Date);
            endDate = myFormat.format(end_Date);
            admissionReport = parksMapper.readTree(Reports.getAdmissionReport(myParks.getAllParksInfo(),
                    myOrder.viewAllOrders(), startDate, endDate).toString());

            return ResponseEntity.ok().body(admissionReport);
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
            admissionReport = parseError;
        }
        return ResponseEntity.badRequest().body(admissionReport);
    }

    @GetMapping("/reports/568")
    public ResponseEntity<JsonNode> getRevenueReport(@RequestParam(value="start_date", defaultValue="") String startDate,
                                        @RequestParam(value="end_date", defaultValue= "") String endDate){
        JsonNode revenueReport = null;
        JsonNode parseError = null;
        requestDate.setLenient(false);
        try {
            String parseErrorString = "{\"type\": \"http://cs.iit.edu/~virgil/cs445/" +
                    "project/api/problems/data-validation\","+
                    "\"title\": \"Your request data didn't pass validation\"," +
                    "\"detail\": \"Wrong date format try (yyyyMMdd)\","+
                    "\"status\": 400,"+
                    "\"instance\": \"/reports/568\"}";
            parseError = parksMapper.readTree(parseErrorString);

            if(startDate.equals("") && endDate.equals("")){
                revenueReport = parksMapper.readTree(Reports.getRevenueReport(myParks.getAllParksInfo(),
                        myOrder.viewAllOrders(), startDate, endDate).toString());

                return ResponseEntity.ok().body(revenueReport);
            }
            if(startDate.equals("")){
                Date end_Date = requestDate.parse(endDate);
                endDate = myFormat.format(end_Date);

                revenueReport = parksMapper.readTree(Reports.getRevenueReport(myParks.getAllParksInfo(),
                        myOrder.viewAllOrders(), startDate, endDate).toString());

                return ResponseEntity.ok().body(revenueReport);
            }
            if(endDate.equals("")){
                Date start_date = requestDate.parse(startDate);
                startDate = myFormat.format(start_date);

                revenueReport = parksMapper.readTree(Reports.getRevenueReport(myParks.getAllParksInfo(),
                        myOrder.viewAllOrders(), startDate, endDate).toString());

                return ResponseEntity.ok().body(revenueReport);
            }


            Date start_Date = requestDate.parse(startDate);
            Date end_Date = requestDate.parse(endDate);
            if(start_Date.compareTo(end_Date)> 0){
                String json = "{\"type\": \"http://cs.iit.edu/~virgil/cs445/" +
                        "project/api/problems/data-validation\","+
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"Start date has to be before end Date\","+
                        "\"status\": 400,"+
                        "\"instance\": \"/reports/568\"}";
                revenueReport = parksMapper.readTree(json);
                return ResponseEntity.badRequest().body(revenueReport);
            }


            startDate = myFormat.format(start_Date);
            endDate = myFormat.format(end_Date);
            revenueReport = parksMapper.readTree(Reports.getRevenueReport(myParks.getAllParksInfo(),
                    myOrder.viewAllOrders(), startDate, endDate).toString());

            return ResponseEntity.ok().body(revenueReport);
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ParseException e){
            revenueReport = parseError;
        }
        return ResponseEntity.badRequest().body(revenueReport);
    }

    @GetMapping("/search")
    public ResponseEntity<JsonNode> searchApplication(@RequestParam(value ="key", defaultValue = "") String key,
                                                      @RequestParam(value="start_date", defaultValue = "") String startDate,
                                                      @RequestParam(value="end_date", defaultValue = "") String endDate){
        JsonNode searchAll = null;
        JsonNode errorParse = null;
        String errorMsg = "{\"type\": \"http://cs.iit.edu/~virgil/cs445/" +
                "project/api/problems/data-validation\","+
                "\"title\": \"Your request data didn't pass validation\"," +
                "\"detail\": \"Format of date is wrong try (yyyyMMdd)\","+
                "\"status\": 400,"+
                "\"instance\": \"/search\"}";
        try {
            errorParse = parksMapper.readTree(errorMsg);

            if(startDate.equals("") && endDate.equals("")) {

            }else if(startDate.equals("")){
                Date endDate_format = requestDate.parse(endDate);
                endDate = myFormat.format(endDate_format);
            }else if(endDate.equals("")){
                Date startDate_format = requestDate.parse(startDate);
                startDate = myFormat.format(startDate_format);
            }else{
                Date endDate_format = requestDate.parse(endDate);
                Date startDate_format = requestDate.parse(startDate);
                if(startDate_format.compareTo(endDate_format) > 0){
                    String dateError = "{\"type\": \"http://cs.iit.edu/~virgil/cs445/" +
                            "project/api/problems/data-validation\","+
                            "\"title\": \"Your request data didn't pass validation\"," +
                            "\"detail\": \"Start date has to be before end Date\","+
                            "\"status\": 400,"+
                            "\"instance\": \"/search\"}";
                    errorParse = parksMapper.readTree(dateError);
                    return ResponseEntity.badRequest().body(errorParse);
                }
                startDate = myFormat.format(startDate_format);
                endDate = myFormat.format(endDate_format);
            }

            searchAll = parksMapper.readTree(Reports.searchApplication(myParks.getParksKey(key), myOrder.searchWithKey(key),
                    myOrder.searchWithKeyVisitor(key), myComment.searchWithKey(key), startDate, endDate).toString());
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            return ResponseEntity.badRequest().body(errorParse);
        }
        return ResponseEntity.ok().body(searchAll);

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
        try {
            if(!(noteInfo.has("vid"))) {
                String errorMsg = "{" +
                        "\"type\": \"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"The vid fieldname is missing\"," +
                        "\"status\": 400," +
                        "\"instance\": \"/parks/" + pid + "\"" +
                        "}";;
                JsonNode errorJson = parksMapper.readTree(errorMsg);
                return new ResponseEntity<JsonNode>(errorJson, HttpStatus.BAD_REQUEST);
            }
            if(!(noteInfo.has("title"))) {
                String errorMsg = "{" +
                        "\"type\": \"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"The title fieldname is missing\"," +
                        "\"status\": 400," +
                        "\"instance\": \"/parks/" + pid + "\"" +
                        "}";;
                JsonNode errorJson = parksMapper.readTree(errorMsg);
                return new ResponseEntity<JsonNode>(errorJson, HttpStatus.BAD_REQUEST);
            }
            if(!(noteInfo.has("text"))) {
                String errorMsg = "{" +
                        "\"type\": \"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"The text fieldname is missing\"," +
                        "\"status\": 400," +
                        "\"instance\": \"/parks/" + pid + "\"" +
                        "}";;
                JsonNode errorJson = parksMapper.readTree(errorMsg);
                return new ResponseEntity<JsonNode>(errorJson, HttpStatus.BAD_REQUEST);
            }

            int vid = noteInfo.get("vid").asInt(-1);
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
        String[] states = {"AK","AL","AR","AS","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID","IL","IN","KS","KY","LA","MA","MD","ME","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY","OH","OK","OR","PA","PR","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"};
        List<String> listOfStates = Arrays.asList(states);
        int pid = -1;
        try {
            String state = orderInfo.path("vehicle").path("state").asText();
            if(!(listOfStates.contains(state.toUpperCase()))){
                String errorMsg = "{" +
                        "\"type\": \"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"You entered an invalid state\"," +
                        "\"status\": 400," +
                        "\"instance\": \"/orders"+
                        "}";
                JsonNode errorJson = parksMapper.readTree(errorMsg);
                return new ResponseEntity<JsonNode>(errorJson, HttpStatus.BAD_REQUEST);
            }
            String plate = orderInfo.path("vehicle").path("plate").asText();
            String type = orderInfo.path("vehicle").path("type").asText();
            if(plate.equals("") || type.equals("")){
                String errorMsg = "{" +
                        "\"type\": \"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"Your vehicle information is incomplete\"," +
                        "\"status\": 400," +
                        "\"instance\": \"/orders"+
                        "}";
                JsonNode errorJson = parksMapper.readTree(errorMsg);
                return new ResponseEntity<JsonNode>(errorJson, HttpStatus.BAD_REQUEST);
            }
            String name = orderInfo.path("visitor").path("name").asText();
            String email = orderInfo.path("visitor").path("email").asText();
            if(email.equals("") || !(isAnEmail(email))){
                String errorMsg = "{" +
                        "\"type\": \"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"Please enter a valid email\"," +
                        "\"status\": 400," +
                        "\"instance\": \"/orders"+
                        "}";
                JsonNode errorJson = parksMapper.readTree(errorMsg);
                return new ResponseEntity<JsonNode>(errorJson, HttpStatus.BAD_REQUEST);
            }
            String card = orderInfo.path("visitor").path("payment_info").path("card").asText();
            String cardName = orderInfo.path("visitor").path("payment_info").path("name_on_card").asText();
            String expirationDate = orderInfo.path("visitor").path("payment_info").path("expiration_date").asText();
            int zip = orderInfo.path("visitor").path("payment_info").path("zip").asInt();
            if(card.equals("")||cardName.equals("")||expirationDate.equals("")||zip == 0){
                String errorMsg = "{" +
                        "\"type\": \"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"Please enter a valid Paymentinformation\"," +
                        "\"status\": 400," +
                        "\"instance\": \"/orders"+
                        "}";
                JsonNode errorJson = parksMapper.readTree(errorMsg);
                return new ResponseEntity<JsonNode>(errorJson, HttpStatus.BAD_REQUEST);
            }


            if(orderInfo.has("pid")) {
                pid = orderInfo.get("pid").asInt(-1);
            }else{
                String errorMsg = "{" +
                    "\"type\": \"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                    "\"title\": \"Your request data didn't pass validation\"," +
                    "\"detail\": \"The pid fieldname is missing\"," +
                    "\"status\": 400," +
                    "\"instance\": \"/orders"+
                    "}";
                JsonNode errorJson = parksMapper.readTree(errorMsg);
                return new ResponseEntity<JsonNode>(errorJson, HttpStatus.BAD_REQUEST);
            }
            if(pid == -1 || myParks.getIndexOfPark(pid) == -1) {
                String errorMsg = "{" +
                        "\"type\": \"http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation\"," +
                        "\"title\": \"Your request data didn't pass validation\"," +
                        "\"detail\": \"The pid isn't valid\"," +
                        "\"status\": 400," +
                        "\"instance\": \"/orders"+
                        "}";
                JsonNode errorJson = parksMapper.readTree(errorMsg);
                return new ResponseEntity<JsonNode>(errorJson, HttpStatus.BAD_REQUEST);
            }
            double amount = myParks.getParkFee(pid, type, state);
            Vehicle vehicleInfo = new Vehicle(state, plate, type);
            PaymentInfo paymentInfo = new PaymentInfo(zip, card, cardName, expirationDate);
            if(name.equals("")){
                name = cardName;
            }
            int oid = myOrder.createNewOrder(pid,amount, vehicleInfo,paymentInfo,new Date(), name, email);
            String json = "{\"oid\":\"" + oid+"\"}";

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

    private boolean isAnEmail(String email) {
        if(!(email.contains("@")) || !(email.contains("."))){
            return false;
        }
        return true;
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
