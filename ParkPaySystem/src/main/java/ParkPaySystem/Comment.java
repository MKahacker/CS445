package ParkPaySystem;

import java.util.Date;

public class Comment {
    //Leaving Commments is directly related to payments.
    String author;
    Date timeStamp;
    String body;

    public Comment(String author, Date timeStamp, String body){
        this.author = author;
        this.timeStamp = timeStamp;
        this.body = body;
    }

    public String viewComment(){
        return "Author: " + this.author + "\n" + "Posted On: " + this.timeStamp.toString() + "\n" + this.body;
    }

    public String viewCommentBody(){
        return this.body;
    }

    public boolean equals(Comment otherComment){
        return (this.author.equals(otherComment.author)&& this.timeStamp.equals(otherComment.timeStamp) && this.body.equals(otherComment.body));
    }
}

