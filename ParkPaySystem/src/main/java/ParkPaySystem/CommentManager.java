package ParkPaySystem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommentManager {
    List<Comment> commentList;

    public CommentManager(List<Comment> commentList){
        this.commentList = commentList;
    }

    public int createNewComment(int parkId, int vistorId, Date timeStamp, String title, String body){
        int commentId = commentList.size()+100;
        Comment newComment = new Comment(commentId, parkId, vistorId, timeStamp,title, body);

        this.commentList.add(newComment);
        return commentId;
    }

    public JSONArray viewCommentsForPark(int parkId){
        JSONArray parkComments = new JSONArray();
        for(int i= 0; i < commentList.size(); i++){
            if(commentList.get(i).getParkId() == parkId){
                parkComments.put(commentList.get(i).limitedCommentInfo());
            }
        }
        return parkComments;
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

    public JSONArray viewAllComments(){
        JSONArray comments = new JSONArray();
        Set<Integer> parkIdSet = new HashSet<Integer>();

        return comments;
    }


}
