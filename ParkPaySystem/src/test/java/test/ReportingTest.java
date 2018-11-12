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

    private JSONArray generatePark() {
        JSONArray parks = new JSONArray();
        int[] pids = new int[]{123, 124, 131};
        String[] names = new String[]{"Apple River Canyon", "Castle Rock", "Mermet Lake"};

        for(int i = 0; i < 3; i++) {
            JSONObject parksInfo = new JSONObject();
            JSONObject locationInfo = new JSONObject();

            parksInfo.put("pid", Integer.toString(pids[i]));
            locationInfo.put("name", names[i]);
            parksInfo.put("location_info", locationInfo);
            parks.put(parksInfo);
        }
        return parks;
    }

    @Test
    public void returnAllParksIds(){
        JSONArray orders = generateOrder();
        assertEquals("[124, 123]", Reports.returnAllParkIds(orders).toString());
    }

    @Test
    public void testSearchApplication(){
        assertEquals("[]", Reports.searchApplication(new JSONArray(), new JSONArray(), new JSONArray(),new JSONArray()
                ,"", "").toString());
    }


}
