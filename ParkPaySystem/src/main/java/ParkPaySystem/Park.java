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
        this.comments[0] = "default";
        this.location = "default";
    }

    public Park (String name, double fee, String[] comments, String location){
        this.name = name;
        this.fee = fee;
        this.comments = comments;
        this.location = location;
    }

    public Boolean equals(Park otherPark){
        return (this.name.equals(otherPark.name) && this.fee == otherPark.fee && this.comments.equals(otherPark.comments) && this.location.equals(otherPark.location));
    }

    public String viewInformation(){
        String stringfiedName = "Name = " + this.name + ",\n";
        String stringfiedFee = "Fee = $" + String.format("%.2f",this.fee) + ",\n";
        String stringfiedComments = "Comments = \n";
        for(int i = 0; i < comments.length; i++) {
             stringfiedComments = stringfiedComments + "\t" + this.comments[i] + ",\n";
        }
        String stringfiedLocation = "Location = " + this.location;
        return stringfiedName + stringfiedFee + stringfiedComments + stringfiedLocation;
    }



}
