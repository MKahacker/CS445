package ParkPaySystem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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

        this.commentList.add(newComment);
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

    public JSONArray viewAllComments(){
        JSONArray innerArray = new JSONArray();
        JSONArray comments = new JSONArray();
        JSONObject objectInArray = new JSONObject();
        List<Integer> parkIdSet = new ArrayList<Integer>();

        for(int i = 0; i < this.commentList.size(); i++) {
            if (!(parkIdSet.contains(this.commentList.get(i).getParkId()))) {
                objectInArray.put("pid", this.commentList.get(i).getParkId());
                objectInArray.put("notes", viewCommentsForPark(this.commentList.get(i).getParkId()));
                parkIdSet.add(this.commentList.get(i).getParkId());
                comments.put(objectInArray);
            }
        }


        return comments;
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



}
