package test;

import Comment.Comment;
import Comment.CommentManager;
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
    Comment testComment;

    @BeforeEach
    public void initTest() {
        myComments = new CommentManager(createCommentList());
        newComment = new Comment(200, 250, 350, new Date(), "Green Forest", "This forest was soo green");
        testComment = new Comment(107, 250, 100, new Date(), "Lovely Day", "");
    }

    public List<Comment> createCommentList() {
        comments = new ArrayList<Comment>();
        Comment comment;
        String title = "Like";
        String body = "I Like very much";
        for (int i = 0; i < 5; i++) {
            comment = new Comment(i + 100, i + 200, i + 300, new Date(), title + " " + Integer.toString(i), body);
            comments.add(comment);
        }
        return comments;
    }

    @Test
    public void testCreateNewComment() {
        int commentId = myComments.createNewComment(250, 350, new Date(), "Green Forest", "This forest was soo green");
        newComment.setId(commentId);
        assertEquals(newComment.viewComment().toString(), myComments.viewSpecificComment(commentId).toString());
    }

    @Test
    public void testFindSpecificCommentExists() {
        comments.add(newComment);
        CommentManager commentManager = new CommentManager(comments);

        assertEquals(newComment.viewComment().toString(), commentManager.viewSpecificComment(200).toString());
    }

    @Test
    public void testFindSpecificCommentIllegal() {
        assertEquals("{}", myComments.viewSpecificComment(200).toString());
    }

    @Test
    public void testViewAllCommentAssociatedWithOneParkWithOnlyOneComment() {
        int commentId = myComments.createNewComment(250, 350, new Date(), "Green Forest", "This forest was soo green");
        newComment.setId(commentId);

        assertEquals("[" + newComment.limitedCommentInfo().toString() + "]", myComments.viewCommentsForPark(250).toString());
    }

    @Test
    public void testViewAllCommentsAssociatedWithOneParkWithManyComments() {
        int commentId;
        Date testDate = new Date();
        List<Comment> testComments = new ArrayList<Comment>();
        JSONArray expectedOutput = new JSONArray();

        for (int i = 0; i < 6; i++) {
            commentId = myComments.createNewComment(250, 350, testDate, "Green Forest", "This forest was soo green");
            testComments.add(new Comment(commentId, 250, 350, testDate, "Green Forest", "This forest was soo green"));
            expectedOutput.put(testComments.get(i).limitedCommentInfo());
        }
        assertEquals(expectedOutput.toString(), myComments.viewCommentsForPark(250).toString());
    }

    @Test
    public void testViewAllCommentsAssociatedWithParkWithNoComments() {
        assertEquals("[]", myComments.viewCommentsForPark(250).toString());
    }

    @Test
    public void testViewAllCommentsThatExistWithNoComments() {
        List<Comment> noCommentList = new ArrayList<Comment>();
        CommentManager noComments = new CommentManager(noCommentList);

        assertEquals("[]", noComments.viewAllComments().toString());
    }

    @Test
    public void testViewAllCommentsWithOneComment() {
        List<Comment> oneCommentList = new ArrayList<Comment>();
        oneCommentList.add(testComment);
        CommentManager oneComments = new CommentManager(oneCommentList);

        String stringComment = "[{\"notes\":[" + testComment.limitedCommentInfo() + "],\"pid\":250}]";
        assertEquals(stringComment, oneComments.viewAllComments().toString());
    }

    @Test
    public void testViewAllCommentsWithManyCommentsFromOnePark() {
        String commentsInfo = testComment.limitedCommentInfo() + "," + newComment.limitedCommentInfo();
        String stringComment = "[{\"notes\":[" + commentsInfo + "],\"pid\":250}]";
        comments.clear();
        comments.add(testComment);
        comments.add(newComment);

        CommentManager manyComments = new CommentManager(comments);
        assertEquals(stringComment, manyComments.viewAllComments().toString());
    }

    @Test
    public void testViewAllCommentsWithManyCommentsFromManyParks() {
        Comment testComment2 = new Comment(108, 160, 100, new Date(), "Lovely Day", "");
        String stringComment = "[{\"notes\":[" + testComment.limitedCommentInfo() + "],\"pid\":250},{\"notes\":[" + testComment2.limitedCommentInfo() + "],\"pid\":160}]";

        comments.clear();
        comments.add(testComment);
        comments.add(testComment2);

        CommentManager manyComments = new CommentManager(comments);
        assertEquals(stringComment, manyComments.viewAllComments().toString());
    }

    @Test
    public void testUpdateComment() {
        int nid = myComments.createNewComment(250, 502, new Date(), "Hey", "About to write");
        myComments.updateComment(nid, 402, "Mosquitos galore", "The mosquitos kill here");
        Comment updatedComment = new Comment(nid, 250, 402, new Date(), "Mosquitos galore", "The mosquitos kill here");
        assertEquals(updatedComment.viewComment().toString(), myComments.viewSpecificComment(nid).toString());
    }

    @Test
    public void testUpdateWhenCommentIdNotExist(){
        int returnFrom = myComments.updateComment(1000, 402, "Mosquitos galore", "The mosquitos kill here");
        assertEquals(-1, returnFrom);
    }

    @Test
    public void testDeleteComment(){
        int nid = myComments.createNewComment(250, 502, new Date(), "Hey", "About to write");;
        myComments.deleteComment(nid);
        assertEquals("{}", myComments.viewSpecificComment(nid).toString());
    }

}