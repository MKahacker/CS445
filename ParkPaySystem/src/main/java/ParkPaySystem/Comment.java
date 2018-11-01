package ParkPaySystem;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    int id;
    int parkId;
    int authorId;
    Date timeStamp;
    String title;
    String body;

    public Comment(int id, int parkId, int authorId, Date timeStamp, String title,  String body){
        this.id = id;
        this.parkId = parkId;
        this.authorId = authorId;
        this.timeStamp = timeStamp;
        this.title = title;
        this.body = body;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getParkId(){
        return this.parkId;
    }

    public String viewCommentBody(){
        return this.body;
    }

    public void updateComment(int authorId, String title, String body) {
        this.authorId = authorId;
        this.title = title;
        this.body = body;
    }

    public JSONObject limitedCommentInfo(){
        JSONObject commentInfo = new JSONObject();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        commentInfo.put("nid", this.id);
        commentInfo.put("date", formatter.format(this.timeStamp));
        commentInfo.put("title", this.title);

        return commentInfo;
    }

    public JSONObject viewComment(){
        JSONObject comment = new JSONObject();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        comment.put("nid", this.id);
        comment.put("pid", this.parkId);
        comment.put("vid", this.authorId);
        comment.put("date", formatter.format(this.timeStamp));
        comment.put("title", this.title);
        comment.put("text", this.body);

        return comment;
    }

}

