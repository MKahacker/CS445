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
        string.put("pid", this.id);
        string.put("name", this.name);
        string.put("region", this.region);
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


}
