package ParkPaySystem;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    //Leaving Commments is directly related to payments.
    int id;
    int parkId;
    int author;
    Date timeStamp;
    String title;
    String body;

    public Comment(int id, int parkId, int author, Date timeStamp, String title,  String body){
        this.id = id;
        this.parkId = parkId;
        this.author = author;
        this.timeStamp = timeStamp;
        this.title = title;
        this.body = body;
    }

    public int getParkId(){
        return this.parkId;
    }

    public String viewCommentBody(){
        return this.body;
    }

    public JSONObject viewComment(){
        JSONObject comment = new JSONObject();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        comment.put("nid", this.id);
        comment.put("pid", this.parkId);
        comment.put("vid", this.author);
        comment.put("date", formatter.format(this.timeStamp));
        comment.put("title", this.title);
        comment.put("body", this.body);

        return comment;
    }
}

