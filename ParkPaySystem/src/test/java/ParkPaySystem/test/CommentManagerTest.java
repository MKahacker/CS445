package ParkPaySystem.test;

import ParkPaySystem.Comment;
import ParkPaySystem.CommentManager;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentManagerTest {
    CommentManager myComments;
    List<Comment> comments;
    Comment newComment;
    @BeforeEach
    public void initTest(){
        myComments = new CommentManager(createCommentList());
        newComment = new Comment(200, 250, 350, new Date(), "Green Forest", "This forest was soo green");

    }

    public List<Comment> createCommentList(){
        comments = new ArrayList<Comment>();
        Comment comment;
        String title = "Like";
        String body = "I Like very much";
        for(int i = 0; i < 5; i++){
            comment = new Comment(i+100, i+200, i+300, new Date(),title+" "+Integer.toString(i),body);
            comments.add(comment);
        }
        return comments;
    }

    @Test
    public void testCreateNewComment(){
        int commentId = myComments.createNewComment(250, 350,new Date(), "Green Forest", "This forest was soo green");
        newComment.setId(commentId);
        assertEquals(newComment.viewComment().toString(), myComments.viewSpecificComment(commentId).toString());
    }

    @Test
    public void testFindSpecificCommentExists(){
        comments.add(newComment);
        CommentManager commentManager = new CommentManager(comments);

        assertEquals(newComment.viewComment().toString(),commentManager.viewSpecificComment(200).toString());
    }

    @Test
    public void testFindSpecificCommentIllegal(){
        assertEquals("{}", myComments.viewSpecificComment(200).toString());
    }

    @Test
    public void testViewAllCommentAssociatedWithOneParkWithOnlyOneComment(){
        int commentId = myComments.createNewComment(250, 350,new Date(), "Green Forest", "This forest was soo green");
        newComment.setId(commentId);

        assertEquals("["+newComment.limitedCommentInfo().toString()+"]", myComments.viewCommentsForPark(250).toString());
    }

    @Test
    public void testViewAllCommentsAssociatedWithOneParkWithManyComments(){
        int commentId;
        Date testDate = new Date();
        List<Comment> testComments = new ArrayList<Comment>();
        JSONArray expectedOutput = new JSONArray();

        for(int i = 0; i < 6; i++) {
            commentId = myComments.createNewComment(250, 350, testDate, "Green Forest", "This forest was soo green");
            testComments.add(new Comment(commentId, 250, 350,testDate, "Green Forest", "This forest was soo green"));
            expectedOutput.put(testComments.get(i).limitedCommentInfo());
        }
        assertEquals(expectedOutput.toString(), myComments.viewCommentsForPark(250).toString());
    }

    @Test
    public void testViewAllCommentsAssociatedWithParkWithNoComments(){
        assertEquals("[]", myComments.viewCommentsForPark(250).toString());
    }

}