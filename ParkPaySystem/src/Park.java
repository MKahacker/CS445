package ParkPaySystem;

import java.lang.String;

public class Park {
    private String name;
    private double fee;;
    private String[] comments;
    private String location;

    public Park(){
        this.name = "default";
        this.fee = 0.00;
        this.comments = new String[1];
        this.location = "default";
    }

    public Park (String name, double fee, String[] comments, String location){
        this.name = name;
        this.fee = fee;
        this.comments = comments;
        this.location = location;
    }



}
