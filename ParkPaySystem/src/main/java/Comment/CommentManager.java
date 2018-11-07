package Comment;


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

    public int updateComment(int id, int authorId, String title, String body) {
        int idx = returnIndexOfComment(id);

        if(idx != -1) {
            this.commentList.get(idx).updateComment(authorId, title, body);
        }

        return idx;
    }

    public void deleteComment(int nid) {
        int idx = returnIndexOfComment(nid);
        if(idx != -1){
            this.commentList.remove(idx);
        }
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
        List<Integer> parkIdSet = returnAllParkIds();

        for(int j=0; j < parkIdSet.size(); j++) {
            JSONObject objectInArray = new JSONObject();
            objectInArray.put("pid", parkIdSet.get(j));
            objectInArray.put("notes", viewCommentsForPark(parkIdSet.get(j)));
            comments.put(objectInArray);
        }
        return comments;
    }

    public List<Integer> returnAllParkIds(){
        List<Integer> parkIdSet = new ArrayList<Integer>();
        for(int i = 0; i < this.commentList.size(); i++) {
            if (!(parkIdSet.contains(this.commentList.get(i).getParkId()))) {
                parkIdSet.add(this.commentList.get(i).getParkId());
            }
        }

        return parkIdSet;
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
