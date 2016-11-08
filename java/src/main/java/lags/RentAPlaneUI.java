package lags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class RentAPlaneUI {
	private RentAPlaneService service;
	private boolean hasUserQuit = false;

	public boolean hasUserQuit() {
		return hasUserQuit;
	}

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

    public void removeOrder() throws IOException {
        System.out.println("DELETE ORDER");
        System.out.println("ID:");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String id = br.readLine();

        service.removeOrderAndWriteToFile(id);
    }

	public void calculateAndShowGrossSales() {
        System.out.println("CALCULATING GS..");
        double ca = service.calculateAndShowGrossSales();
        System.out.print("GS: ");
        System.out.printf(new DecimalFormat("#.##").format(ca));
        System.out.println();
	}
	
	public void evaluateCommand(char command) throws IOException {
		switch (command)
		{
		    case 'Q':
		    {
		    	hasUserQuit = true;
		    	break;
		    }
		    case 'L':
		    {
		        showOrderList();
		        break;
		    }
		    case 'A':
		    {
		        addOrder();
		        break;
		    }
		    case 'S':
		    {
				removeOrder();
		        break;
		    }
		    case 'C':
		    {
		        calculateAndShowGrossSales();
		        break;
		    }
		}
	}

	public char getUserCommand() throws IOException {
		char command;
		do
		{
		    System.out.println("A)DD ORDER  L)IST   C)ACLCULATE GS  S)UPPRESS  Q)UIT");
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    command = br.readLine().charAt(0);
		    command = Character.toUpperCase(command);
		    System.out.println();
		} while (command != 'A' && command != 'L' && command != 'S' && command != 'Q' && command != 'C');
		return command;
	}


}
