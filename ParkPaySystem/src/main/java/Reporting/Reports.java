package Reporting;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reports {

    public static JSONObject getAdmissionReport(JSONArray parks, JSONArray order, Date startDate, Date endDate){
        JSONObject admissionReports = new JSONObject();
        admissionReports.put("rid", Integer.toString(567));
        admissionReports.put("name", "Admissions report");
        if(startDate==null){
            admissionReports.put("start_date","");
        }
        if(endDate == null){
            admissionReports.put("end_date", "");
        }
        admissionReports.put("total_admissions",order.length());
        admissionReports.put("detail_by_park",getOrdersForPark(order, parks));
        return admissionReports;
    }

    private static JSONArray getOrdersForPark(JSONArray order, JSONArray parks) {
        List<Integer> parksId = returnAllParkIds(order);
        JSONArray parkInfo = new JSONArray();
        for(int i = 0; i < parks.length(); i++) {
            JSONObject parkObject = new JSONObject();
            parkObject.put("pid", parks.getJSONObject(i).get("pid"));
            parkObject.put("name", parks.getJSONObject(i).getJSONObject("location_info").get("name"));
            parkObject.put("admissions", countAdmissions(order, parks.getJSONObject(i).getInt("pid")));
            parkInfo.put(parkObject);
        }
        return parkInfo;
    }

    private static int countAdmissions(JSONArray order, int pid) {
        int count = 0;
        for(int i = 0; i < order.length(); i++){
            if(order.getJSONObject(i).getInt("pid")==pid){
                count+=1;
            }
        }
        return count;
    }

    public static List<Integer> returnAllParkIds(JSONArray order){
        List<Integer> parkIdSet = new ArrayList<Integer>();
        for(int i = 0; i < order.length(); i++) {
            if (!(parkIdSet.contains(order.getJSONObject(i).getInt("pid")))) {
                parkIdSet.add(order.getJSONObject(i).getInt("pid"));
            }
        }

        return parkIdSet;
    }

    public static String getReport() {
        return "[{\"rid\":\"567\",\"name\":\"Admissions report\"}," +
                "{\"mrid\":\"568\",\"name\":\"Revenue report\"}]";
    }

}
