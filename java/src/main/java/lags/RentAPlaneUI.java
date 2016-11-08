package lags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RentAPlaneUI {
	private RentAPlaneService service;
	
	public void setService(RentAPlaneService service) {
		this.service = service;
	}

	public RentAPlaneUI() {}
	
    public void showOrderList() {
        System.out.println("ORDERS LIST");
        System.out.println(String.format("%-8s %10s %5s %10s", "ID", "DEBUT", "DUREE", "PRIX"));
        System.out.println(String.format("%-8s %10s %5s %10s", "--------", "-------", "-----", "----------"));

        service.getOrdersSortByDepartureDate().stream()
            .forEach(this::showOrder);

        System.out.println(String.format("%-8s %10s %5s %10s", "--------", "-------", "-----", "----------"));
    }

    private void showOrder(Order order) {
        System.out.println(String.format("%-8s %10d %5d %10f", order.getId(), order.getDepartureDateYYYYDD(), order.getDurationInDays(), order.getPrice()));
    }

	public void addOrder() throws IOException {		
        System.out.println("ADD ORDER");
        System.out.println("FORMAT = ID;STARTT;END;PRICE");
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();

        line = line.toUpperCase();
        String[] fields = line.split(";");
        String id = fields[0];
        int st = Integer.parseInt(fields[1]);
        int dur = Integer.parseInt(fields[2]);
        double pr = Double.parseDouble(fields[3]);
        Order order = new Order(id, st, dur, pr);
        service.addOrderAndWriteToFile(order);
	}

}
