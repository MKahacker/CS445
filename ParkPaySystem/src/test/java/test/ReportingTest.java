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
                "{\"mrid\":\"568\",\"name\":\"Revenue report\"}]", Reports.getReport());
    }

    @Test
    public void getAdmissionsReport(){
        String emptyParksAndOrders = "{\"end_date\":\"\",\"detail_by_park\":[],\"total_admissions\":0," +
                "\"name\":\"Admissions report\",\"rid\":\"567\",\"start_date\":\"\"}";

        assertEquals(emptyParksAndOrders, Reports.getAdmissionReport(new JSONArray(), new JSONArray(),null,null).toString());

        JSONArray parks = generatePark();
        JSONArray orders = generateOrder();

        assertEquals("{\"end_date\":\"\"," +
                "\"detail_by_park\":[{\"name\":\"Apple River Canyon\",\"pid\":\"123\"," +
                "\"admissions\":2},{\"name\":\"Castle Rock\",\"pid\":\"124\"," +
                "\"admissions\":2},{\"name\":\"Mermet Lake\",\"pid\":\"131\"," +
                "\"admissions\":0}],\"total_admissions\":4," +
                "\"name\":\"Admissions report\",\"rid\":\"567\"," +
                "\"start_date\":\"\"}",
                Reports.getAdmissionReport(parks, orders,null,null).toString());
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



}
