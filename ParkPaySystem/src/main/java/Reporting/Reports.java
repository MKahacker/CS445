package Reporting;

import org.json.JSONArray;
import org.json.JSONObject;

public class Reports {

    public static JSONObject getAdmissionReport(JSONArray parks, JSONArray orders){
        JSONObject admissionReports = new JSONObject();
        return admissionReports;
    }

    public static String getReport() {
        return "[{\"rid\":\"567\",\"name\":\"Admissions report\"}," +
                "{\"mrid\":\"568\",\"name\":\"Revenue report\"}]";
    }
}
