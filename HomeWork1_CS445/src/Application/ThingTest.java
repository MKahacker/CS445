package Application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThingTest {
    @Test
    public void testingIfConstructorSetsName(){
        Thing aThing = new Thing("Test");
        assertEquals("Test", aThing.getName());
    }

}