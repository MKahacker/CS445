package ParkPaySystem;


import java.lang.String;
import org.json.JSONObject;


public class Park implements InterfacePark {
    private int id;
    private String name;
    private String region;
    private String phone;
    private String web;
    private String address;
    private Geolocation geo;
    private Payment[] fee;

    public Park (int id, String name, String region, String address,
                 String phone, String web, Geolocation geo, Payment[] fee){
        this.id = id;
        this.name = name;
        this.region = region;
        this.phone = phone;
        this.web = web;
        this.address = address;
        this.geo = geo;
        this.fee = fee;
    }

    public Park (int id, String name, String address, String web, Geolocation geo, Payment[] fee){
        this.id = id;
        this.name = name;
        this.web = web;
        this.geo = geo;
        this.address = address;
        this.fee = fee;
        this.region = "";
        this.phone = "";
    }

    public Geolocation getGeo(){
        return this.geo;
    }

    public int getParkId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }


    public String viewInformation(){
        JSONObject string = new JSONObject();
        JSONObject locationInfo = new JSONObject();
        JSONObject geoInfo = new JSONObject();
        string.put("pid", this.id);


       /* String stringfiedName = "Name = " + this.name + ",\n";
        String stringfiedFee = "Fee = $" + String.format("%.2f",this.fee) + ",\n";
        String stringfiedComments = "Comments = \n";
        for(int i = 0; i < comments.length; i++) {
             stringfiedComments = stringfiedComments + "\t" + this.comments[i].viewCommentBody() + ",\n";
        }
        String stringfiedLocation = "Location = " + this.location + "\n";
        return stringfiedId + stringfiedName + stringfiedFee + stringfiedComments + stringfiedLocation;*/
        return "";
    }
    public JSONObject parseLocationInfo(){
        JSONObject locationInfo = new JSONObject();
        locationInfo.put("name", this.name);
        locationInfo.put("region", this.region);
        locationInfo.put("phone", this.phone);
        locationInfo.put("web", this.web);

        return locationInfo;
    }

    public JSONObject parseGeoInfo(){
        JSONObject geoInfo  = new JSONObject();
        geoInfo.put("lat", Double.toString(this.geo.getLat()));
        geoInfo.put("lng", Double.toString(this.geo.getLng()));
        return geoInfo;
    }


}
