package lags;

import java.io.IOException;

public class Main {

	static RentAPlaneUI ui;

    public static void main(String[] args) throws IOException
    {
        initApplication();
        runApplication();
    }

	private static void initApplication() {
		RentAPlaneService service = new RentAPlaneService();
        service.loadOrdersFromFile("../LQORDRES.CSV");
        ui = new RentAPlaneUI();
        ui.setService(service);
	}

	private static void runApplication() throws IOException {
		while (!ui.hasUserQuit())
        {
            char command = ui.getUserCommand();
            ui.evaluateCommand(command);
        }
	}
}
