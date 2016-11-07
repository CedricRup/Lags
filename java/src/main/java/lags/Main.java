package lags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static final boolean debug = true;

    public static void main(String[] args) throws IOException
    {
        LagsService service = new LagsService();
        service.getFileOrder("../LQORDRES.CSV");
        boolean hasUserQuit = false;        
        while (!hasUserQuit)
        {
            char command = getUserCommand();
            switch (command)
            {
                case 'Q':
                {
                    hasUserQuit = true;
                    break;
                }
                case 'L':
                {
                    service.list();
                    break;
                }
                case 'A':
                {
                    service.addOrder();
                    break;
                }
                case 'S':
                {
                    service.suppress();
                    break;
                }
                case 'C':
                {
                    service.calculateTheGS(debug);
                    break;
                }
            }
        }
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
