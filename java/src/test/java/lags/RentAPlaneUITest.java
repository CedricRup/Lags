package lags;

import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RentAPlaneUITest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    private RentAPlaneUI ui = new RentAPlaneUI();

    RentAPlaneService mockService;

    @Before
    public void initMocks() {
        mockService = mock(RentAPlaneService.class);
        ui.setService(mockService);
    }

    @Test
    public void listDisplayOrdersCorrectly() {
        // Arrange
        when(mockService.getOrdersSortByDepartureDate()).thenReturn(createStubOrders());
        // Act
        ui.showOrderList();
        // Assert
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
        // Arrange
        systemInMock.provideLines("DONALD;2015001;006;10000.00");
        // Act
        ui.addOrder();
        // Assert
        assertEquals("ADD ORDER\nFORMAT = ID;STARTT;END;PRICE\n", systemOutRule.getLog());
        verify(mockService, times(1)).addOrderAndWriteToFile(new Order("DONALD", 2015001, 6, 10000.00));
    }

    @Test
    public void removeOrderDisplaysAndCallServiceCorrect() throws IOException {
        // Arrange
        systemInMock.provideLines("DONALD");
        // Act
        ui.removeOrder();
        // Assert
        assertEquals("DELETE ORDER\nID:\n", systemOutRule.getLog());
        verify(mockService, times(1)).removeOrderAndWriteToFile("DONALD");
    }
    
    @Test
    public void calculateAndShowGrossSalesDisplaysCorrectly() {
    	// Arrange
        when(mockService.calculateAndShowGrossSales()).thenReturn(19000.00);
        // Act
        ui.calculateAndShowGrossSales();
    	// Assert
        String expected = "CALCULATING GS..\n"+
        	"GS: 19000\n";
        assertEquals(expected, systemOutRule.getLog());
    }

    private List<Order> createStubOrders() {
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(new Order("DONALD", 2015001, 6, 10000.00));
        expectedOrders.add(new Order("DAISY", 2015003, 2, 4000.00));
        expectedOrders.add(new Order("PICSOU", 2015007, 7, 8000.00));
        expectedOrders.add(new Order("MICKEY", 2015008, 7, 9000.00));
        return expectedOrders;
    }

}
