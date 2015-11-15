using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace Lags
{
    
    class Program
    {
        const bool debug = true;
        // ==================
        // main function
        // ===================

        static void Main(string[] args)
        {
            LagsService service = new LagsService();
            service.getFichierOrder("ORDRES.CSV");
            bool flag = false;
            // while not at the end of program
            while (!flag)
            {
                // set command to Z
                Char command = 'Z';
                while (command != 'A' && command != 'L' && command != 'S' && command != 'Q' && command != 'C')
                {
                    Console.WriteLine("A)DD ORDER  L)IST   C)ALCULATE GS  S)UPPRESS  Q)UIT");
                    ConsoleKeyInfo keyInfo = Console.ReadKey();
                    command = Char.ToUpper(keyInfo.KeyChar);
                    Console.WriteLine();
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
                            service.List();
                            break;
                        }
                    case 'A':
                        {
                            service.AddOrder();
                            break;
                        }
                    case 'S':
                        {
                            service.Delete();
                            break;
                        }
                    case 'C':
                        {
                            service.CalculateTheGS(debug);
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
}