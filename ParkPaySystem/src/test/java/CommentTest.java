package test.java;

import org.junit.jupiter.api.Test;
import main.java.ParkPaySystem.*;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    @Test
    public void testViewComment(){
        Date timeStamp = new Date();
        Comment newComment = new Comment("Jeff", timeStamp, "Lovely Park!");

        assertEquals("Author: Jeff\nPosted On: "+timeStamp.toString()+"\nLovely Park!", newComment.viewComment());
    }

    @Test
    public void testViewCommentBody(){
        Date timeStamp = new Date();
        Comment newComment = new Comment("Jeff", timeStamp, "Lovely Park!");

        assertEquals("Lovely Park!", newComment.viewCommentBody());
    }

    @Test
    public void testIfTwoIdenticalCommentsAreEqual(){
        Date timeStamp = new Date();
        Comment firstComment = new Comment("Jeff", timeStamp, "Lovely!");
        Comment secondComment = new Comment ("Jeff", timeStamp, "Lovely!");
        boolean results = firstComment.equals(secondComment);

        assertEquals(true, results);
    }

    @Test
    public void testIfCommentsNotEqualFails(){
        Date timeStamp = new Date();
        Comment firstComment = new Comment("Jeff", timeStamp, "Lovely!");
        Comment secondComment = new Comment ("Jeff", timeStamp, "Lovely Day!");
        boolean results = firstComment.equals(secondComment);

        assertEquals(false, results);
    }

}