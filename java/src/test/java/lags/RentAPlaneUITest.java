package lags;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class RentAPlaneUITest {
	
	@Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	
    private static final String TESTDATA_FILENAME = RentAPlaneServiceTest.class.getResource("TestData.csv").getPath();
    
    @Test
    public void listDisplayOrdersCorrectly() {
        RentAPlaneService service = createStubOrders();
        RentAPlaneUI.showOrderList(service);
        assertEquals("ORDERS LIST\n" +
            "ID            DEBUT DUREE       PRIX\n" +
            "--------    ------- ----- ----------\n" +
            "DONALD      2015001     6 10000,000000\n" +
            "DAISY       2015003     2 4000,000000\n" +
            "PICSOU      2015007     7 8000,000000\n" +
            "MICKEY      2015008     7 9000,000000\n" +
            "--------    ------- ----- ----------\n", systemOutRule.getLog());
    }

    private RentAPlaneService createStubOrders() {
        RentAPlaneService service = new RentAPlaneService();
        service.loadOrdersFromFile(TESTDATA_FILENAME);
        return service;
    }
}
