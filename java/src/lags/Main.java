package lags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static final boolean debug = true;
    // ==================
    // main function
    // ===================

    public static void main(String[] args) throws IOException
    {
        LagsService service = new LagsService();
        service.getFileOrder("..\\ORDRES.CSV");
        boolean flag = false;
        // While it's not the end
        while (!flag)
        {
            // command is now Z
            char command = 'Z';
            while (command != 'A' && command != 'L' && command != 'S' && command != 'Q' && command != 'C')
            {
                System.out.println("A)DD ORDER  L)IST   C)ACLCULATE GS  S)UPPRESS  Q)UIT");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                command = br.readLine().charAt(0);
                command = Character.toUpperCase(command);
                System.out.println();
            }
            switch (command)
            {
                case 'Q':
                {
                    flag = true;
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


    //// lit le fihier des ordres et calcule le CA
    //static void getFichierOrder(String fileName)
    //{
    //    try
    //    {
    //        using (var reader = new StreamReader(fileName))
    //        {
    //            while (!reader.EndOfStream)
    //            {
    //                var champs = reader.ReadLine().Split(';');
    //                String chp1 = champs[0];
    //                int chp2 = Int32.Parse(champs[1]);
    //                int champ3 = Int32.Parse(champs[2]);
    //                double chp4 = Double.Parse(champs[3]);
    //                Ordre ordre = new Ordre(chp1, chp2, champ3, chp4);
    //                laListe.Add(ordre);
    //            }
    //        }
    //    }
    //    catch (FileNotFoundException e)
    //    {
    //        Console.WriteLine("FICHIER ORDRES.CSV NON TROUVE. CREATION FICHIER.");
    //        WriteOrdres(fileName);
    //    }
    //}

}
