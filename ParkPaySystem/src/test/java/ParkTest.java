package test.java;

import main.java.ParkPaySystem.*;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ParkTest {

    @Test
    public void testConstructor(){
        Comment [] comments = new Comment[5];
        Park park1 = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");

        assertEquals(park1, park1);
    }

    @Test
    public void testEqualsTrue(){
        Comment [] comments = new Comment[5];
        Comment [] otherComment = new Comment[5];
        Park park1 = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");
        Park park2 = new Park(101, "Yosemite", 12.0, otherComment, "123 Something Ln");
        Boolean results = park1.equals(park2);

        assertEquals(Boolean.TRUE, results);
    }

    @Test
    public void testEqualsFalse(){
        Comment [] comments = new Comment[5];
        Park park1 = new Park(101,"Yosemite", 12.0, comments, "123 Something Ln");
        Park park2 = new Park(102, "Cherokee", 12.0, comments, "123 Something Ln");
        Boolean results = park1.equals(park2);

        assertEquals(Boolean.FALSE, results);
    }

    @Test
    public void testViewInformationGivesTheRightInformation(){
        Comment[] comments = new Comment[1];
        Date timeStamp = new Date();
        Comment oneComment = new Comment("Jeff",timeStamp,"Lovely");
        comments[0] = oneComment;
        Park firstPark = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");

        assertEquals("Id = 101,\nName = Yosemite,\nFee = $12.00,\nComments = \n\tLovely,\nLocation = 123 Something Ln", firstPark.viewInformation());
    }

    @Test
    public void testViewInformationWithMultipleComments(){
        Comment[] comments = new Comment[3];
        Date timeStamp = new Date();
        for(int i = 0; i < 3; i++){
            comments[i] = new Comment("Jeff",timeStamp,"Lovely" + Integer.toString(i));
        }
        Park firstPark = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");

        assertEquals("Id = 101,\nName = Yosemite,\nFee = $12.00,\nComments = \n\tLovely0,\n\tLovely1,\n\tLovely2,\nLocation = 123 Something Ln", firstPark.viewInformation());
    }

    @Test
    public void testViewInformationWithZeroComments(){
        Comment[] comments = new Comment[0];
        Park firstPark = new Park(101, "Yosemite", 12.0, comments, "123 Something Ln");

        assertEquals("Id = 101,\nName = Yosemite,\nFee = $12.00,\nComments = \nLocation = 123 Something Ln", firstPark.viewInformation());
    }

    @Test
    public void testCheckCommentsWhenCommentsAreSame(){
        Date timeStamp = new Date();
        Comment[] comments1 = new Comment[2];
        Comment[] comments2 = new Comment[2];
        Comment firstComment = new Comment("Jeff", timeStamp, "Lovely");
        Comment secondComment = new Comment ("Jeff", timeStamp, "Lovely");
        comments1[0] = comments2[0] = firstComment;
        comments1[1] = comments2[1] = secondComment;
        Park testPark = new Park(101, "Yosemite", 12.00, comments1, "123 Something ln");

        assertEquals(true, testPark.checkComments(comments1,comments2));
    }

    @Test
    public void testCheckCommentsWhenCommentsAreDifferent(){
        Date timeStamp = new Date();
        Comment[] comments1 = new Comment[2];
        Comment[] comments2 = new Comment[2];
        Comment firstComment = new Comment("Jeff", timeStamp, "Lovely");
        Comment secondComment = new Comment ("Jeff", timeStamp, "Lovely1");
        comments1[0] = firstComment;
        comments2[0] = secondComment;
        comments1[1] = secondComment;
        comments2[1] = firstComment;
        Park testPark = new Park(101, "Yosemite", 12.00, comments1, "123 Something ln");

        assertEquals (comments1[1].equals(comments2[1]), testPark.checkComments(comments1,comments2));
    }

}