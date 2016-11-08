package lags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class RentAPlaneServiceTest {
    private static final String TESTDATA_FILENAME = RentAPlaneServiceTest.class.getResource("TestData.csv").getPath();

    @Test
    public void loadOrdersFromFileWorks() {
        RentAPlaneService service = createStubOrders();
        assertTrue(service.getOrders().size() == 4);
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(new Order("DONALD", 2015001, 6, 10000.00));
        expectedOrders.add(new Order("DAISY", 2015003, 2, 4000.00));
        expectedOrders.add(new Order("PICSOU", 2015007, 7, 8000.00));
        expectedOrders.add(new Order("MICKEY", 2015008, 7, 9000.00));
        assertEquals(expectedOrders, service.getOrders());
    }



    @Test
    public void emptyOrdersWillReturnZeroGrossSales() {
        RentAPlaneService service = new RentAPlaneService();
        assertEquals(0.00, service.calculateGrossSales(Collections.emptyList()), 0.0);
    }
    
    @Test
    public void exampleOrderCalculatesGrossSaleOf19000() {
    	RentAPlaneService service = new RentAPlaneService();
        List<Order> orderList = new ArrayList<>();
		orderList.add(new Order("DONALD",2015001, 6,10000.00));
		orderList.add(new Order("DAISY", 2015003, 2, 4000.00));
		orderList.add(new Order("PICSOU", 2015007, 7, 8000.00));
		orderList.add(new Order("MICKEY",2015008, 7, 9000.00));
		assertEquals(19000.0, service.calculateGrossSales(orderList), 0.0);
    }

    @Test(expected=IllegalArgumentException.class)
    @Ignore
    public void ordersSpanningOverYearChangeShouldntWork() {
        RentAPlaneService service = new RentAPlaneService();
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order("DONALD", 2015365, 10, 100));
        orderList.add(new Order("MICKEY", 2016001, 1, 100));
        assertEquals(100.0, service.calculateGrossSales(orderList), 0.0);
    }

    private RentAPlaneService createStubOrders() {
        RentAPlaneService service = new RentAPlaneService();
        service.loadOrdersFromFile(TESTDATA_FILENAME);
        return service;
    }

}
