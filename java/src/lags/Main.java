package lags;

import java.io.IOException;

public class Main {

    static final boolean debug = true;
    // ==================
    // fonction principale
    // ===================

    public static void main(String[] args) throws IOException
    {
        LagsService service = new LagsService();
        service.getFichierOrder("..\\ORDRES.CSV");
        boolean flag = false;
        // tant que ce n'est pas la fin du programme
        while (!flag)
        {
            // met la commande à Z
            char commande = 'Z';
            while (commande != 'A' && commande != 'L' && commande != 'S' && commande != 'Q' && commande != 'C')
            {
                System.out.println("A)JOUTER UN ORDRE  L)ISTER   C)ALCULER CA  S)UPPRIMER  Q)UITTER");
                commande = (char)System.in.read();
                commande = Character.toUpperCase(commande);
                System.out.println();
            }
            switch (commande)
            {
                case 'Q':
                {
                    flag = true;
                    break;
                }
                case 'L':
                {
                    service.liste();
                    break;
                }
                case 'A':
                {
                    service.ajouterOrdre();
                    break;
                }
                case 'S':
                {
                    service.suppression();
                    break;
                }
                case 'C':
                {
                    service.calculerLeCA(debug);
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
