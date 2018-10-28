package ParkPaySystem;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class CommentManager {
    List<Comment> commentList;

    public CommentManager(List<Comment> commentList){
        this.commentList = commentList;
    }

    public int createNewComment(int parkId, int vistorId, Date timeStamp, String title, String body){
        int commentId = commentList.size()+100;
        Comment newComment = new Comment(commentId, parkId, vistorId, timeStamp,title, body);

        return commentId;
    }

    public JSONObject viewSpecificComment(int id){
        JSONObject comment = new JSONObject();
        int indexOfComment = returnIndexOfComment(id);
        if (indexOfComment != -1) {
            comment = this.commentList.get(indexOfComment).viewComment();
        }
        return comment;

    }

    public int returnIndexOfComment(int id){
        int idx = -1;
        for(int i = 0; i < this.commentList.size(); i++){
            if(this.commentList.get(i).getId() == id){
                idx = i;
                return idx;
            }
        }
        return idx;
    }

}
