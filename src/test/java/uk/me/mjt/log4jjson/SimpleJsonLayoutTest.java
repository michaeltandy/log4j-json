/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.me.mjt.log4jjson;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author michael
 */
public class SimpleJsonLayoutTest {
    
    private static final Logger LOG = Logger.getLogger(SimpleJsonLayoutTest.class);
    
    public SimpleJsonLayoutTest() {
    }
    
    @Test
    public void testDemonstration() {
        LOG.info("Example of some logging");
        LOG.warn("Some text\nwith a newline",new Exception("Outer Exception",new Exception("Nested Exception")));
        LOG.fatal("Text may be complicated & have many symbols\n¬!£$%^&*()_+{}:@~<>?,./;'#[]-=`\\| \t\n");
        
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
    
}
