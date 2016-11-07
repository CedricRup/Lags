package lags;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LagsServiceTest {

	private static final String TESTDATA_FILENAME = "/Users/tobias/Projects/Lags/java/src/test/resources/lags/TestData.csv";

	@Test
	public void getFileOrderCreatesOrders() {

		LagsService service = new LagsService();
		service.loadOrdersFromFile(TESTDATA_FILENAME);
		assertTrue(service.listOrder.size() == 4);
		List<Order> expectedOrders = new ArrayList<>();
		expectedOrders.add(new Order("DONALD", 2015001, 6, 10000.00));
		expectedOrders.add(new Order("DAISY", 2015003, 2, 4000.00));
		expectedOrders.add(new Order("PICSOU", 2015007, 7, 8000.00));
		expectedOrders.add(new Order("MICKEY", 2015008, 7, 9000.00));
		assertEquals(expectedOrders, service.listOrder);
	}

}
