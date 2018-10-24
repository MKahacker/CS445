package main.java.ParkPaySystem;

import java.lang.String;

public class Park implements InterfacePark{
    private int id;
    private String name;
    private double fee;
    private Comment[] comments;
    private String location;

    public Park (int id, String name, double fee, Comment[] comments, String location){
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.comments = (comments == null)? new Comment[0] : comments;
        this.location = location;
    }

    public Boolean equals(Park otherPark){
        Boolean commentEqual = this.comments.length == otherPark.comments.length;
        if(commentEqual == Boolean.TRUE){
            commentEqual = commentEqual && checkComments(this.comments, otherPark.comments);
        }
        return (this.id == otherPark.id && this.name.equals(otherPark.name) && this.fee == otherPark.fee && commentEqual && this.location.equals(otherPark.location));
    }

    public boolean checkComments(Comment[] firstComment, Comment[] secondComment){
        boolean commentSame = false;
        for(int i = 0; i < 2; i++){
            if(firstComment[i] == null && secondComment[i] == null){
                commentSame = true;
            }else if(firstComment[i] == null || secondComment[i]== null){
                commentSame = false;
            }else{
                commentSame = true;
                commentSame = commentSame && firstComment[i].equals(secondComment[i]);
            }
        }
        return commentSame;
    }

    public String viewInformation(){
        String stringfiedId = "Id = " + Integer.toString(this.id) + ",\n";
        String stringfiedName = "Name = " + this.name + ",\n";
        String stringfiedFee = "Fee = $" + String.format("%.2f",this.fee) + ",\n";
        String stringfiedComments = "Comments = \n";
        for(int i = 0; i < comments.length; i++) {
             stringfiedComments = stringfiedComments + "\t" + this.comments[i].viewCommentBody() + ",\n";
        }
        String stringfiedLocation = "Location = " + this.location + "\n";
        return stringfiedId + stringfiedName + stringfiedFee + stringfiedComments + stringfiedLocation;
    }

    public int getParkId(){
        return this.id;
    }


}
