package project.aurora.wdc.parser;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;


public class WDCReaderTest {

    @Test
    public void testReader() throws IOException {
        var kpDateSourceFile = new File(this.getClass().getResource("/wdc/kp2019.wdc").getFile());
        var wdcReader = new WDCFileReader(kpDateSourceFile);

        assertNotNull(wdcReader);
    }

}
