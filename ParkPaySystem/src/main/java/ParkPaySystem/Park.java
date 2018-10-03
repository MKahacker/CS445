package main.java.ParkPaySystem;

import java.lang.String;

public class Park {
    private int id;
    private String name;
    private double fee;;
    private String[] comments;
    private String location;

    public Park (int id, String name, double fee, String[] comments, String location){
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.comments = comments;
        this.location = location;
    }

    public Boolean equals(Park otherPark){
        return (this.id == otherPark.id && this.name.equals(otherPark.name) && this.fee == otherPark.fee && this.comments.equals(otherPark.comments) && this.location.equals(otherPark.location));
    }

    public String viewInformation(){
        String stringfiedId = "Id = " + Integer.toString(this.id) + ",\n";
        String stringfiedName = "Name = " + this.name + ",\n";
        String stringfiedFee = "Fee = $" + String.format("%.2f",this.fee) + ",\n";
        String stringfiedComments = "Comments = \n";
        for(int i = 0; i < comments.length; i++) {
             stringfiedComments = stringfiedComments + "\t" + this.comments[i] + ",\n";
        }
        String stringfiedLocation = "Location = " + this.location;
        return stringfiedId + stringfiedName + stringfiedFee + stringfiedComments + stringfiedLocation;
    }



}
