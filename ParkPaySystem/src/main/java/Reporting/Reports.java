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

    public static String getReport() {
        return "[{\"rid\":\"567\",\"name\":\"Admissions report\"}," +
                "{\"rid\":\"568\",\"name\":\"Revenue report\"}]";
    }

    public static JSONObject getAdmissionReport(JSONArray parks, JSONArray order, String startDate, String endDate){
        JSONObject admissionReports = new JSONObject();
        admissionReports.put("rid", Integer.toString(567));
        admissionReports.put("name", "Admissions report");
        admissionReports.put("start_date",startDate);
        admissionReports.put("end_date", endDate);
        try {
            if (!(startDate.equals(""))) {
                order = removeDatesBefore(order, startDate);
            }

            if (!(endDate.equals(""))) {
                order = removeDatesAfter(order, endDate);
            }
        }catch (ParseException e){

        }

        admissionReports.put("total_admissions",order.length());
        admissionReports.put("detail_by_park",getAdmissionOrdersForPark(order, parks));
        return admissionReports;
    }

    public static JSONObject getRevenueReport(JSONArray parks, JSONArray order, String startDate, String endDate){
        JSONObject revenueReport = new JSONObject();
        revenueReport.put("rid", Integer.toString(568));
        revenueReport.put("name", "Revenue report");
        revenueReport.put("start_date", startDate);
        revenueReport.put("end_date", endDate);
        try {
            if (!(startDate.equals(""))) {
                order = removeDatesBefore(order, startDate);
            }

            if (!(endDate.equals(""))) {
                order = removeDatesAfter(order, endDate);
            }
        }catch (ParseException e){

        }
        revenueReport.put("total_orders",order.length());
        revenueReport.put("total_revenue", sumRevenue(order));
        revenueReport.put("detail_by_park", getRevenueOrders(order, parks));
        return revenueReport;
    }


    private static JSONArray getAdmissionOrdersForPark(JSONArray order, JSONArray parks) {
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

    private static JSONArray getRevenueOrders(JSONArray order, JSONArray parks) {
        JSONArray parkInfo = new JSONArray();
        for(int i = 0; i < parks.length(); i++) {
            JSONObject parkObject = new JSONObject();
            parkObject.put("pid", parks.getJSONObject(i).get("pid"));
            parkObject.put("name", parks.getJSONObject(i).getJSONObject("location_info").get("name"));
            int pid = parks.getJSONObject(i).getInt("pid");
            parkObject.put("orders", countAdmissions(order, pid));
            parkObject.put("revenue", sumRevenueForPark(order,pid));
            parkInfo.put(parkObject);
        }
        return parkInfo;
    }

    private static double sumRevenue(JSONArray order) {
        double amount = 0;
        for(int i = 0; i < order.length(); i++){
            amount+=order.getJSONObject(i).getDouble("amount");
        }
        return amount;
    }

    private static double sumRevenueForPark(JSONArray order, int pid) {
        double revenue = 0;
        for(int i = 0; i < order.length(); i++){
            if(order.getJSONObject(i).getInt("pid")==pid){
                revenue+=order.getJSONObject(i).getDouble("amount");
            }
        }
        return revenue;
    }

    private static JSONArray removeDatesAfter(JSONArray order, String endDate) throws ParseException {
        DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray ArrayWithValidDates = new JSONArray();

            Date actualStartDate = myFormat.parse(endDate);
            for(int i = 0; i < order.length(); i++){
                Date orderDate = myFormat.parse(order.getJSONObject(i).getString("date"));
                if(actualStartDate.compareTo(orderDate) >= 0){
                    ArrayWithValidDates.put(order.getJSONObject(i));
                }
            }
            return ArrayWithValidDates;

    }

    private static JSONArray removeDatesBefore(JSONArray order, String startDate) throws ParseException {
        DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray ArrayWithValidDates = new JSONArray();

            Date actualStartDate = myFormat.parse(startDate);
            for(int i = 0; i < order.length(); i++){
                Date orderDate = myFormat.parse(order.getJSONObject(i).getString("date"));
                if(actualStartDate.compareTo(orderDate) <= 0){
                    ArrayWithValidDates.put(order.getJSONObject(i));
                }
            }
            return ArrayWithValidDates;

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



}
