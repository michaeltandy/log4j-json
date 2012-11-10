package uk.me.mjt.log4jjson;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Tandy
 */
public class SimpleJsonLayoutTest {
    
    private static final Logger LOG = Logger.getLogger(SimpleJsonLayoutTest.class);
    
    public SimpleJsonLayoutTest() {
    }
    
    @Test
    public void testDemonstration() {
        TestAppender.baos.reset();
        
        LOG.info("Example of some logging");
        LOG.warn("Some text\nwith a newline",new Exception("Outer Exception",new Exception("Nested Exception")));
        LOG.fatal("Text may be complicated & have many symbols\nÂ¬!Â£$%^&*()_+{}:@~<>?,./;'#[]-=`\\| \t\n");
        
        String whatWasLogged = TestAppender.baos.toString();
        String[] lines = whatWasLogged.split("\n");
        
        assertEquals(3,lines.length);
        assertTrue(lines[0].contains("INFO"));
        assertTrue(lines[0].contains("Example of some logging"));
        
        assertTrue(lines[1].contains("newline"));
        assertTrue(lines[1].contains("Outer Exception"));
        assertTrue(lines[1].contains("Nested Exception"));
        
        assertTrue(lines[2].contains("have many symbols"));
    }
    
    @Test
    public void testObjectHandling() {
        TestAppender.baos.reset();
        
        LOG.info(new Object() {
            @Override
            public String toString() {
                throw new RuntimeException("Hypothetical failure");
            }
        });
        LOG.warn(null);
        
        String whatWasLogged = TestAppender.baos.toString();
        String[] lines = whatWasLogged.split("\n");
        assertEquals(2,lines.length);
        
        assertTrue(lines[0].contains("Hypothetical"));
        assertTrue(lines[1].contains("WARN"));
    }
    
}
