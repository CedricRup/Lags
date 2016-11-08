package lags;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class RentAPlaneUITest {
	
	@Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	
	@Rule
	public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();
	
    private static final String TESTDATA_FILENAME = RentAPlaneServiceTest.class.getResource("TestData.csv").getPath();
    
    private RentAPlaneUI ui = new RentAPlaneUI();
    
    @Test
    public void listDisplayOrdersCorrectly() {
        RentAPlaneService service = createStubOrders();
        ui.setService(service);
        ui.showOrderList();
        assertEquals("ORDERS LIST\n" +
            "ID            DEBUT DUREE       PRIX\n" +
            "--------    ------- ----- ----------\n" +
            "DONALD      2015001     6 10000,000000\n" +
            "DAISY       2015003     2 4000,000000\n" +
            "PICSOU      2015007     7 8000,000000\n" +
            "MICKEY      2015008     7 9000,000000\n" +
            "--------    ------- ----- ----------\n", systemOutRule.getLog());
    }
    
    @Test
    public void addOrderAsksUserForOrderInCorrectFormat() throws IOException {
    	RentAPlaneService service = new RentAPlaneService();
    	systemInMock.provideLines("DONALD;2015001;006;10000.00");
    	ui.setService(service);
    	ui.addOrder();
    	assertEquals("ADD ORDER\nFORMAT = ID;STARTT;END;PRICE\n", systemOutRule.getLog());
    	assertTrue(service.getOrders().size() == 1);
    	assertEquals(new Order("DONALD", 2015001, 6, 10000.00), service.getOrders().get(0));
    }

    private RentAPlaneService createStubOrders() {
        RentAPlaneService service = new RentAPlaneService();
        service.loadOrdersFromFile(TESTDATA_FILENAME);
        return service;
    }
}
