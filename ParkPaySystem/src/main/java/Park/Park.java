package Park;


import Fee.Payment;
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

    public void setParkId(int parkId){
        this.id = parkId;
    }

    public Geolocation getGeo(){
        return this.geo;
    }

    public int getParkId(){
        return this.id;
    }

    public JSONObject viewInformation(){
        JSONObject string = new JSONObject();

        string.put("pid", Integer.toString(this.id));
        string.put("location_info", parseLocationInfo());

        return string;
    }
    public JSONObject parseLocationInfo(){
        JSONObject locationInfo = new JSONObject();
        locationInfo.put("name", this.name);
        locationInfo.put("region", this.region);
        locationInfo.put("phone", this.phone);
        locationInfo.put("web", this.web);
        locationInfo.put("address", this.address);
        locationInfo.put("geo", parseGeoInfo());

        return locationInfo;
    }

    public JSONObject parseGeoInfo(){
        JSONObject geoInfo  = new JSONObject();
        geoInfo.put("lat", this.geo.getLat());
        geoInfo.put("lng", this.geo.getLng());
        return geoInfo;
    }

    public double inStateFee(int type){
        try{
          return fee[type].getInStateFee();
       }catch (ArrayIndexOutOfBoundsException e){
            return -1;
        }
    }

    public double outStateFee(int type){
        try{
           return fee[type].getOutStateFee();
        }catch(ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }


}
