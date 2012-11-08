package uk.me.mjt.log4jjson;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;

/**
 * Log appender that writes to a byte array, for unit test purposes. You 
 * probably don't want to use this for anything other than testing logging.
 * @author Michael Tandy
 */
public class TestAppender extends WriterAppender {
    public static final ByteArrayOutputStream baos = new ByteArrayOutputStream(16*1024);
    public static final OutputStreamWriter w = new OutputStreamWriter(baos);
    
    public TestAppender() {
        setWriter(w);
    }
    
    public TestAppender(Layout layout) {
        setWriter(w);
        setLayout(layout);
    }
}
