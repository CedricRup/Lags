package lags;

public class RentAPlaneUI {
	
	private RentAPlaneUI() {}
	
    public static void showOrderList(RentAPlaneService service) {
        System.out.println("ORDERS LIST");
        System.out.println(String.format("%-8s %10s %5s %10s", "ID", "DEBUT", "DUREE", "PRIX"));
        System.out.println(String.format("%-8s %10s %5s %10s", "--------", "-------", "-----", "----------"));

        service.getOrdersSortByDepartureDate().stream()
            .forEach(RentAPlaneUI::showOrder);

        System.out.println(String.format("%-8s %10s %5s %10s", "--------", "-------", "-----", "----------"));
    }

    private static void showOrder(Order order) {
        System.out.println(String.format("%-8s %10d %5d %10f", order.getId(), order.getDepartureDateYYYYDD(), order.getDurationInDays(), order.getPrice()));
    }

}
