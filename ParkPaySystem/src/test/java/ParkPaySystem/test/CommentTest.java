package ParkPaySystem.test;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ParkPaySystem.Comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    Comment newComment;
    JSONObject expected;
    JSONObject limitedExpected;
    Date timeStamp = new Date();
    DateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void initTests(){
        int id = 101;
        int parkId = 101;
        int author = 675;
        String title = "Lovely Park!";
        String body = "I love this park";
        newComment = new Comment(id,parkId,author,timeStamp, title, body);
        expected =jsonBuilder(id, parkId, author, title, body);
        limitedExpected = limitedJSONBuilder(id, title);
    }

    public JSONObject jsonBuilder(int id, int parkId, int author, String title, String body){
        JSONObject comment = new JSONObject();

        comment.put("nid", id);
        comment.put("pid", parkId);
        comment.put("vid", author);
        comment.put("date", formatter.format(timeStamp));
        comment.put("title", title);
        comment.put("body", body);

        return comment;
    }

    public JSONObject limitedJSONBuilder(int id, String title){
        JSONObject limitedComment = new JSONObject();

        limitedComment.put("nid", id);
        limitedComment.put("date", formatter.format(timeStamp));
        limitedComment.put("title", title);

        return limitedComment;
    }

    @Test
    public void testViewComment(){
        assertEquals(expected.toString(), newComment.viewComment().toString());
    }

    @Test
    public void testViewCommentBody(){
        assertEquals("I love this park", newComment.viewCommentBody());
    }

    @Test
    public void testViewLimitedComment(){
        assertEquals(limitedExpected.toString(), newComment.limitedCommentInfo().toString());
    }



}