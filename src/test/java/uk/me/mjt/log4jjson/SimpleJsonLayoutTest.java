package uk.me.mjt.log4jjson;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.Priority;
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
    
    @Test
    public void testLogMethod() {
        // Test for https://github.com/michaeltandy/log4j-json/issues/1
        TestAppender.baos.reset();
        
        LOG.log("asdf", Priority.INFO, "this is the log message", null);
        
        String whatWasLogged = TestAppender.baos.toString();
        String[] lines = whatWasLogged.split("\n");
        assertEquals(1,lines.length);
        assertTrue(lines[0].contains("this is the log message"));
    }
    
    @Test
    public void testMinimumLevelForSlowLogging() {
        TestAppender.baos.reset();
        
        LOG.info("Info level logging");
        LOG.debug("Debug level logging");
        
        String whatWasLogged = TestAppender.baos.toString();
        String[] lines = whatWasLogged.split("\n");
        assertEquals(2,lines.length);
        
        assertTrue(lines[0].contains("INFO"));
        assertTrue(lines[0].contains("classname"));
        assertTrue(lines[0].contains("filename"));
        assertTrue(lines[0].contains("linenumber"));
        assertTrue(lines[0].contains("methodname"));
        
        assertTrue(lines[1].contains("DEBUG"));
        assertFalse(lines[1].contains("classname"));
        assertFalse(lines[1].contains("filename"));
        assertFalse(lines[1].contains("linenumber"));
        assertFalse(lines[1].contains("methodname"));
    }
    
    @Test
    public void testSelectiveMdcLogging() {
        TestAppender.baos.reset();
        
        MDC.put("asdf", "value_for_key_asdf");
        MDC.put("qwer", "value_for_key_qwer");
        MDC.put("thread", "attempt to overwrite thread in output");
        
        LOG.info("Example of some logging");
        
        MDC.clear();
        
        String whatWasLogged = TestAppender.baos.toString();
        String[] lines = whatWasLogged.split("\n");
        
        assertEquals(1,lines.length);
        assertTrue(lines[0].contains("value_for_key_asdf"));
        assertFalse(lines[0].contains("value_for_key_qwer"));
        
        assertTrue(lines[0].contains("thread"));
        assertFalse(lines[0].contains("attempt to overwrite thread in output"));
    }
    
}
