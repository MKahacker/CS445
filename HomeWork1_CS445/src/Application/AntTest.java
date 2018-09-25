package Application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AntTest {

    @Test
    public void testsIfConstructorSetsName(){
        Ant anAnt = new Ant("Jason");
        assertEquals("Jason", anAnt.getName());
    }


}