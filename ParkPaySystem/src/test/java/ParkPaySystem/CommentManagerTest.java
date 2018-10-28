package ParkPaySystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentManagerTest {
    CommentManager myComments;

    @BeforeEach
    public void initTest(){
        myComments = new CommentManager(createCommentList());
    }

    public List<Comment> createCommentList(){
        List<Comment> comments = new ArrayList<Comment>();
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

    }

    @Test
    public void testFindSpeficComment(){
        Comment newComment;
        myComments.viewSpecificComment(100);

    }

}