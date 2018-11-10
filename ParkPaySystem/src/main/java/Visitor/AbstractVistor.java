package Visitor;

import org.json.JSONObject;

public abstract class AbstractVistor {
    int id;
    String name;
    String email;

    public JSONObject viewVisitorInfo(){
        JSONObject visitorInfo =  new JSONObject();
        visitorInfo.put("name", this.name);
        visitorInfo.put("email", this.email);

        return visitorInfo;
    }

    public int getVid() {
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }
}
