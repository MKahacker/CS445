package test;


import Reporting.Reports;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReportingTest {

    @Test
    public void getReportIds(){
        assertEquals("[{\"rid\":\"567\",\"name\":\"Admissions report\"}," +
                "{\"rid\":\"568\",\"name\":\"Revenue report\"}]", Reports.getReport());
    }

    @Test
    public void getAdmissionsReport(){
        String emptyParksAndOrders = "{\"end_date\":\"\",\"detail_by_park\":[],\"total_admissions\":0," +
                "\"name\":\"Admissions report\",\"rid\":\"567\",\"start_date\":\"\"}";

        assertEquals(emptyParksAndOrders, Reports.getAdmissionReport(new JSONArray(), new JSONArray(),"","").toString());

        JSONArray parks = generatePark();
        JSONArray orders = generateOrder();

        assertEquals("{\"end_date\":\"\"," +
                "\"detail_by_park\":[{\"name\":\"Apple River Canyon\",\"pid\":\"123\"," +
                "\"admissions\":2},{\"name\":\"Castle Rock\",\"pid\":\"124\"," +
                "\"admissions\":2},{\"name\":\"Mermet Lake\",\"pid\":\"131\"," +
                "\"admissions\":0}],\"total_admissions\":4," +
                "\"name\":\"Admissions report\",\"rid\":\"567\"," +
                "\"start_date\":\"\"}",
                Reports.getAdmissionReport(parks, orders,"","").toString());

        String admissionReportWithOutOfRangeDate = "{\"end_date\":\"2017-02-03\",\"detail_by_park\":" +
                "[{\"name\":\"Apple River Canyon\",\"pid\":\"123\",\"admissions\":0}," +
                "{\"name\":\"Castle Rock\",\"pid\":\"124\",\"admissions\":0}," +
                "{\"name\":\"Mermet Lake\",\"pid\":\"131\",\"admissions\":0}],\"total_admissions\":0," +
                "\"name\":\"Admissions report\",\"rid\":\"567\",\"start_date\":\"2017-01-03\"}";

        assertEquals(admissionReportWithOutOfRangeDate, Reports.getAdmissionReport(parks,orders,"2017-01-03", "2017-02-03").toString());

        String admissionReportWithTwoDatesInRange = "{\"end_date\":\"2018-07-04\",\"detail_by_park\":" +
                "[{\"name\":\"Apple River Canyon\",\"pid\":\"123\",\"admissions\":1}," +
                "{\"name\":\"Castle Rock\",\"pid\":\"124\",\"admissions\":1}," +
                "{\"name\":\"Mermet Lake\",\"pid\":\"131\",\"admissions\":0}],\"total_admissions\":2," +
                "\"name\":\"Admissions report\",\"rid\":\"567\",\"start_date\":\"2017-01-03\"}";

        assertEquals(admissionReportWithTwoDatesInRange, Reports.getAdmissionReport(parks,orders,"2017-01-03", "2018-07-04").toString());

        String errorParseTest = "{\"end_date\":\"2018-07ree-04\",\"detail_by_park\":" +
                "[{\"name\":\"Apple River Canyon\",\"pid\":\"123\",\"admissions\":2}," +
                "{\"name\":\"Castle Rock\",\"pid\":\"124\",\"admissions\":2}," +
                "{\"name\":\"Mermet Lake\",\"pid\":\"131\",\"admissions\":0}],\"total_admissions\":4," +
                "\"name\":\"Admissions report\",\"rid\":\"567\",\"start_date\":\"2017-01tre-03\"}";


        assertEquals(errorParseTest, Reports.getAdmissionReport(parks,orders,"2017-01tre-03", "2018-07ree-04").toString());

    }

    @Test
    public void getRevenueReports(){
        JSONArray parks = new JSONArray();
        JSONArray orders = new JSONArray();

        String emptyParksAndOrders = "{\"end_date\":\"\",\"detail_by_park\":[],\"total_revenue\":0," +
                "\"name\":\"Revenue report\",\"rid\":\"568\",\"total_orders\":0,\"start_date\":\"\"}";

        assertEquals(emptyParksAndOrders,Reports.getRevenueReport(parks,orders,"","").toString());

        parks = generatePark();
        orders = generateOrder();

        assertEquals("{\"end_date\":\"\"," +
                "\"detail_by_park\":[{\"revenue\":23,\"name\":\"Apple River Canyon\",\"pid\":\"123\"," +
                "\"orders\":2},{\"revenue\":13.75,\"name\":\"Castle Rock\",\"pid\":\"124\"," +
                "\"orders\":2},{\"revenue\":0,\"name\":\"Mermet Lake\",\"pid\":\"131\"," +
                "\"orders\":0}],\"total_revenue\":36.75," +
                "\"name\":\"Revenue report\",\"rid\":\"568\",\"total_orders\":4," +
                "\"start_date\":\"\"}",Reports.getRevenueReport(parks,orders,"","").toString());

        assertEquals("{\"end_date\":\"2018-08-31\"," +
                "\"detail_by_park\":[{\"revenue\":13,\"name\":\"Apple River Canyon\",\"pid\":\"123\"," +
                "\"orders\":1},{\"revenue\":9.25,\"name\":\"Castle Rock\",\"pid\":\"124\"," +
                "\"orders\":1},{\"revenue\":0,\"name\":\"Mermet Lake\",\"pid\":\"131\"," +
                "\"orders\":0}],\"total_revenue\":22.25," +
                "\"name\":\"Revenue report\",\"rid\":\"568\",\"total_orders\":2," +
                "\"start_date\":\"2018-08-01\"}",Reports.getRevenueReport(parks,orders,"2018-08-01","2018-08-31").toString());
    }


    @Test
    public void returnAllParksIds(){
        JSONArray orders = generateOrder();
        assertEquals("[124, 123]", Reports.returnAllParkIds(orders).toString());
    }

    @Test
    public void testSearchApplication(){

        String actual = Reports.searchApplication(new JSONArray(), new JSONArray(), new JSONArray(),new JSONArray()
                ,"", "").toString();

        assertEquals("[]", actual);

        JSONArray parks = generatePark();
        JSONArray orders = generateOrder();
        JSONArray visitors = generateVisitors();
        JSONArray notes = generateNotes();

        actual = Reports.searchApplication(parks, orders, visitors,notes, "","").toString();

        assertEquals(returnAllData(parks,orders,visitors,notes), actual);

        actual = Reports.searchApplication(parks, orders, visitors, notes, "2018-08-04", "").toString();
        assertEquals(returnBeforeStart(orders, notes, parks, visitors), actual);

        actual = Reports.searchApplication(parks, orders, visitors, notes, "", "2018-07-03").toString();
        assertEquals(returnAfterEnd(orders, notes, parks, visitors), actual);

        actual = Reports.searchApplication(parks, orders, visitors, notes, "2018-08-01", "2018-08-03").toString();
        assertEquals(returnRange(orders, notes, parks, visitors), actual);


    }

    private String returnRange(JSONArray orders, JSONArray notes, JSONArray parks, JSONArray visitors) {
        JSONArray betweenData = returnParksAndVisitors(parks, visitors);
        betweenData.put(orders.getJSONObject(1));
        return betweenData.toString();
    }

    private String returnAfterEnd(JSONArray orders, JSONArray notes, JSONArray parks, JSONArray visitors) {
        JSONArray EndData = returnParksAndVisitors(parks, visitors);
        EndData.put(orders.getJSONObject(0));
        EndData.put(notes.getJSONObject(0));
        return EndData.toString();
    }

    private String returnBeforeStart(JSONArray orders, JSONArray notes, JSONArray parks, JSONArray visitors) {
        JSONArray startData = returnParksAndVisitors(parks, visitors);
        startData.put(orders.getJSONObject(3));
        startData.put(notes.getJSONObject(1));
        return startData.toString();
    }

    private String returnAllData(JSONArray parks, JSONArray orders, JSONArray visitors, JSONArray notes) {
        JSONArray returnDate = returnParksAndVisitors(parks, visitors);

        for(int i = 0; i < orders.length(); i++){
            returnDate.put(orders.getJSONObject(i));
        }

        for(int i = 0; i < notes.length(); i++){
            returnDate.put(notes.getJSONObject(i));
        }
        return returnDate.toString();
    }

    private JSONArray returnParksAndVisitors(JSONArray parks, JSONArray visitors) {
        JSONArray returnDate = new JSONArray();

        for(int i = 0; i < parks.length(); i++){
            returnDate.put(parks.getJSONObject(i));
        }

        for(int i = 0; i < visitors.length(); i++){
            returnDate.put(visitors.getJSONObject(i));
        }

        return returnDate;
    }



    private JSONArray generatePark() {
        JSONArray parks = new JSONArray();
        int[] pids = new int[]{123, 124, 131};
        String[] names = new String[]{"Apple River Canyon", "Castle Rock", "Mermet Lake"};
        String[] region = {"Northwestern Illinois", "Southern Illinois", "Northwesten Illinois"};
        String[] address = {"8765 N Campbell Ave", "901 N State St","67 Michigan" };
        String[] web = {"wwww.apple.com", "www.casterock.com", "www.mermetlake.com"};
        for(int i = 0; i < 3; i++) {
            JSONObject parksInfo = new JSONObject();
            JSONObject locationInfo = new JSONObject();

            parksInfo.put("pid", Integer.toString(pids[i]));
            locationInfo.put("name", names[i]);
            locationInfo.put("region", region[i]);
            locationInfo.put("address", address[i]);
            locationInfo.put("phone", "");
            locationInfo.put("web", web[i]);
            locationInfo.put("geo", generateGeo().getJSONObject(i));
            parksInfo.put("location_info", locationInfo);
            parks.put(parksInfo);
        }
        return parks;
    }

    private JSONArray generateGeo() {
        JSONArray geoInfo = new JSONArray();
        double[] lat = {37.275, 42.448, 41.978};
        double[] lng = {-88.849, -90.043, -89.364};

        for(int i = 0; i < 3; i++) {
            JSONObject specificParkGeo = new JSONObject();
            specificParkGeo.put("lat", lat[i]);
            specificParkGeo.put("lng", lng[i]);
            geoInfo.put(specificParkGeo);
        }
        return geoInfo;
    }

    private JSONArray generateOrder() {
        JSONArray orders = new JSONArray();
        int[] oid = new int[]{751, 761, 757, 773};
        int[] pid = new int[]{124, 124, 123, 123};
        String[] dateInfo = new String[]{"2018-07-03", "2018-08-02", "2018-07-04", "2018-08-05"};
        String[] type = new String[]{"car", "rv", "car", "rv"};
        double[] amount = new double[]{4.50, 9.25, 10, 13};

        for(int k = 0; k < 4; k++) {
            JSONObject ordersInfo = new JSONObject();
            ordersInfo.put("oid", Integer.toString(oid[k]));
            ordersInfo.put("pid", Integer.toString(pid[k]));
            ordersInfo.put("date", dateInfo[k]);
            ordersInfo.put("type", type[k]);
            ordersInfo.put("amount", amount[k]);
            orders.put(ordersInfo);
        }

        return orders;
    }

    private JSONArray generateVisitors() {
        JSONArray visitors = new JSONArray();
        String[] name = {"James Smith", "John Doe", "Sarah Devon", "George Clooney"};
        String[] email = {"jSmith@google.com", "jDoe@threeletter.org", "Sar456@yahoo.com", "clooney@gmail.com"};
        for(int i = 0; i < 4; i++) {
            JSONObject visitor = new JSONObject();
            visitor.put("name", name[i]);
            visitor.put("email", email[i]);
            visitors.put(visitor);
        }
        return visitors;
    }

    private JSONArray generateNotes() {
        JSONArray notes = new JSONArray();
        String[] nid = {"456", "785"};
        String[] date = {"2018-01-05", "2018-11-12"};
        String[] title = {"Great time!", "Trails are frozen"};
        for(int i = 0; i < 2; i++) {
            JSONObject note = new JSONObject();
            note.put("nid", nid[i]);
            note.put("date", date[i]);
            note.put("title", title[i]);
            notes.put(note);
        }
        return notes;
    }
}
