package Reporting;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reports {

    public static JSONObject getAdmissionReport(JSONArray parks, JSONArray order, String startDate, String endDate){
        JSONObject admissionReports = new JSONObject();
        admissionReports.put("rid", Integer.toString(567));
        admissionReports.put("name", "Admissions report");
        admissionReports.put("start_date",startDate);
        admissionReports.put("end_date", endDate);
        if(!(startDate.equals(""))){
            order = removeDatesBefore(order, startDate);
        }
        if(!(endDate.equals(""))){
            order = removeDatesAfter(order, endDate);
        }

        admissionReports.put("total_admissions",order.length());
        admissionReports.put("detail_by_park",getOrdersForPark(order, parks));
        return admissionReports;
    }

    private static JSONArray removeDatesAfter(JSONArray order, String endDate) {
        DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray ArrayWithValidDates = new JSONArray();
        try {
            Date actualStartDate = myFormat.parse(endDate);
            for(int i = 0; i < order.length(); i++){
                Date orderDate = myFormat.parse(order.getJSONObject(i).getString("date"));
                if(actualStartDate.compareTo(orderDate) >= 0){
                    ArrayWithValidDates.put(order.getJSONObject(i));
                }
            }
            return ArrayWithValidDates;
        }catch(ParseException e){
            e.printStackTrace();

        }
        return ArrayWithValidDates;
    }

    private static JSONArray removeDatesBefore(JSONArray order, String startDate) {
        DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray ArrayWithValidDates = new JSONArray();
        try {
            Date actualStartDate = myFormat.parse(startDate);
            for(int i = 0; i < order.length(); i++){
                Date orderDate = myFormat.parse(order.getJSONObject(i).getString("date"));
                if(actualStartDate.compareTo(orderDate) <= 0){
                    ArrayWithValidDates.put(order.getJSONObject(i));
                }
            }
            return ArrayWithValidDates;
        }catch(ParseException e){
            e.printStackTrace();
            //return order;
        }
        return ArrayWithValidDates;
    }

    private static JSONArray getOrdersForPark(JSONArray order, JSONArray parks) {
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
