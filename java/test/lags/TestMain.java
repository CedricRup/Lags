package lags;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;

public class TestMain {

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Rule
	public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();


	@Test
	public void testMainAndQuit() {
		try {
			systemInMock.provideLines("Q");
			Main.main(new String[] {});
			assertEquals("A)DD ORDER  L)IST   C)ACLCULATE GS  S)UPPRESS  Q)UIT\n\n", systemOutRule.getLog());
		} catch (IOException e) {
			fail();
		}
	}

}
