package mas;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
* 
* @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell
* @version #1.0
* @since 2019/11/06
*/
@RunWith(Suite.class)
@Suite.SuiteClasses({mas.MetaAgentTest.class, mas.PortalTest.class, mas.MessageTypeTest.class, mas.MessageTest.class, mas.RouterTest.class, mas.UserAgentTest.class, mas.MASTest.class})
public class MasSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     * 
     * @throws Exception 
     * @see Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * 
     * @throws Exception 
     * @see Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * 
     * @throws Exception 
     * @see Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
}
