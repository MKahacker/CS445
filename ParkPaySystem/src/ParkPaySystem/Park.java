package ParkPaySystem;

import java.lang.String;

public class Park {
    private String name;
    private double fee;;
    private String[] comments;
    private String location;
    private String state;

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

    public String[] intializeComments(String [] comments){
        for(int i = 0; i < 1; i++){
            comments[i] = "default";
        }
        return comments;
    }

    public Boolean equals(Park otherPark){
        return (this.name.equals(otherPark.name) && this.fee == otherPark.fee && this.comments.equals(otherPark.comments) && this.location.equals(otherPark.location));
    }




}
