package test;


import Reporting.Reports;
import org.json.JSONArray;
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
        assertEquals("{}", Reports.getAdmissionReport(new JSONArray(), new JSONArray()).toString());
    /*    assertEquals("{\"rid\":\"567\"," +
                "\"name\":\"Admissions report\"," +
                "\"start_date\":\"\"," +
                "\"end_date\":\"\"," +
                "\"total_admissions\":4," +
                "\"detail_by_park\":[{\"pid\":\"123\"," +
                "\"name\":\"Apple River Canyon\"," +
                "\"admissions\":2},{\"pid\":\"124\"," +
                "\"name\":\"Castle Rock\"," +
                "\"admissions\":2},{\"pid\":\"131\"," +
                "\"name\":\"Mermet Lake\"," +
                "\"admissions\":0}]}", Reports.getAdmissionReport(new JSONArray(), new JSONArray()));*/
    }

}
