package lags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static final boolean debug = true;

    public static void main(String[] args) throws IOException
    {
        LagsService service = initLagsService();
        boolean hasUserQuit = false;
        while (!hasUserQuit)
        {
            char command = getUserCommand();
            hasUserQuit = evaluateCommand(service, command);
        }
    }

	private static LagsService initLagsService() {
		LagsService service = new LagsService();
        service.loadOrdersFromFile("../LQORDRES.CSV");
		return service;
	}

	private static boolean evaluateCommand(LagsService service, char command) throws IOException {
		boolean hasUserQuit = false;
		switch (command)
		{
		    case 'Q':
		    {
		    	hasUserQuit = true;
		    	break;
		    }
		    case 'L':
		    {
		        service.showOrderList();
		        break;
		    }
		    case 'A':
		    {
		        service.addOrderAndWriteToFile();
		        break;
		    }
		    case 'S':
		    {
		        service.removeOrderAndWriteToFile();
		        break;
		    }
		    case 'C':
		    {
		        service.calculateAndShowGrossSales(debug);
		        break;
		    }
		}
		return hasUserQuit;
	}

	private static char getUserCommand() throws IOException {
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
