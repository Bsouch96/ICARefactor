package mas;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
* The Message class is used to create different types of messages.
* @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell
* @version #1.0
* @since 2019/11/06
*/
public class MessageTypeTest {
    
    public MessageTypeTest() {
    }
   

    /**
     * Test of values method, of class MessageType.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        MessageType[] expResult = null;
        MessageType[] result = MessageType.values();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valueOf method, of class MessageType.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "";
        MessageType expResult = null;
        MessageType result = MessageType.valueOf(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
