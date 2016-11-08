package lags;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.IOException;

public class TestMain {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Test
    public void testMainAndQuit() {
        try {
            systemInMock.provideLines("Q");
            Main.main(new String[]{});
            assertEquals("A)DD ORDER  L)IST   C)ACLCULATE GS  S)UPPRESS  Q)UIT\n\n", systemOutRule.getLog());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void validateCommandAddPrintsCorrectOutput() throws IOException {
        systemInMock.provideLines(
            "A", "DONALD;2015001;006;10000.00",
            "Q");
        Main.main(new String[]{});
        assertThat(systemOutRule.getLog(), containsString(
            "ADD ORDER\n" +
                "FORMAT = ID;STARTT;END;PRICE"));
    }

    @Test
    public void validateExampleOrder() throws IOException {
        systemInMock.provideLines(
            "A", "DONALD;2015001;006;10000.00",
            "A", "DAISY;2015003;002;4000.00",
            "A", "PICSOU;2015007;007;8000.00",
            "A", "MICKEY;2015008;007;9000.00",
            "C",
            "Q");
        Main.main(new String[]{});
        assertThat(systemOutRule.getLog(), containsString("GS: 19000"));
    }

    @Test
    public void validateSupress() throws Exception {
        systemInMock.provideLines(
            "A", "DONALD;2015001;006;10000.00",
            "S", "DONALD",
            "L",
            "Q");
        Main.main(new String[]{});
        assertThat(systemOutRule.getLog(), containsString("ID:"));
    }

}
