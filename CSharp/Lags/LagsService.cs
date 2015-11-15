using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Lags
{
    class LagsService
    {
        List<Order> ListOrder = new List<Order>();

        // read the order fil and calculate GS
        public void getFichierOrder(String fileName)
        {
            try
            {
                using (var reader = new StreamReader(fileName))
                {
                    while (!reader.EndOfStream)
                    {
                        var fields = reader.ReadLine().Split(';');
                        String fld1 = fields[0];
                        int fld2 = Int32.Parse(fields[1]);
                        int field3 = Int32.Parse(fields[2]);
                        double fld4 = Double.Parse(fields[3]);
                        Order order = new Order(fld1, fld2, field3, fld4);
                        ListOrder.Add(order);
                    }
                }
            }
            catch (FileNotFoundException e)
            {
                Console.WriteLine("CSV ORDER FILE NOT FOUND: CREATING ONE.");
                WriteOrders(fileName);
            }
        }
        // write order file
        void WriteOrders(String fileNam)
        {
            using (TextWriter writer = File.CreateText(fileNam))
            {
                foreach (Order order in ListOrder)
                {
                    string[] ligneCSV = new string[4];
                    ligneCSV[0] = order.id;
                    ligneCSV[1] = order.Start.ToString();
                    ligneCSV[2] = order.Duration.ToString();
                    ligneCSV[3] = order.Price.ToString();
                    writer.WriteLine(string.Join(";", ligneCSV));
                }
            }
        }


        // show order list
        public void List()
        {
            Console.WriteLine("ORDERS LIST");
            Console.WriteLine("{0,-8} {1,7} {2,5} {3,10}",
                "ID", "START", "DURATION", "PRICE");
            Console.WriteLine("{0,-8} {1:0000000} {2:00000} {3,10:N2}",
               "--------", "-------", "-----", "----------");
            ListOrder = ListOrder.OrderBy(order => order.Start).ToList();
            ListOrder.ForEach(ShowOrder);
            Console.WriteLine("{0,-8} {1:0000000} {2:00000} {3,10:N2}",
               "--------", "-------", "-----", "----------");
        }

        public void ShowOrder(Order order)
        {
            Console.WriteLine("{0,-8} {1:0000000} {2:00000} {3,10:N2}",
                order.id, order.Start, order.Duration, order.Price);

        }
        // Add order; GS is recalculated
        public void AddOrder()
        {
            Console.WriteLine("ADD ORDER");
            Console.WriteLine("FORMAT = ID;START;END;PRICE");
            String line = Console.ReadLine().ToUpper();
            var fields = line.Split(';');
            String id = fields[0];
            int star = Int32.Parse(fields[1]);
            int dur = Int32.Parse(fields[2]);
            double pr = Double.Parse(fields[3]);
            Order order = new Order(id, star, dur, pr);
            ListOrder.Add(order);
            WriteOrders("ordres.csv");
        }

        //public void CalculerTheGS()
        //{
        //    Console.WriteLine("CALCUL.  GS..");
        //    laListe = laListe.OrderBy(ordre => ordre.debut).ToList();
        //    double ca = CA(laListe);
        //    Console.WriteLine("CA: {0,10:N2}", ca);
        //}

        private double GS(List<Order> orders, bool debug)
        {
            // no order, job done, TROLOLOLO..
            if (orders.Count() == 0)
                return 0.0;
            Order order = orders.ElementAt(0);
            // attention ne marche pas pour les ordres qui depassent la fin de l'année 
            // voir ticket PLAF nO 4807 
            List<Order> list = orders.Where(orderr => orderr.Start >= order.Start + order.Duration).ToList();
            List<Order> list2 = orders.GetRange(1, orders.Count() - 1);
            double gs = order.Price + GS(list, debug);
            // I can live... with or withooooooout youuuuuu
            double gs2 = GS(list2, debug);
            Console.Write(debug ? String.Format("{0,10:N2}\n", Math.Max(gs, gs2)):".");
            return Math.Max(gs, gs2); // LOL
        }

        // File Update
        public void Delete()
        {
            Console.WriteLine("DELETE ORDER");
            Console.Write("ID:");
            string id = Console.ReadLine().ToUpper();
            this.ListOrder = ListOrder.Where(ordre => ordre.id != id).ToList();
            WriteOrders("ORDRES.CSV");
        }



        internal void CalculateTheGS(bool debug)
        {
            Console.WriteLine("CALCUL CA..");
            ListOrder = ListOrder.OrderBy(ordre => ordre.Start).ToList();
            double ca = GS(ListOrder, debug);
            Console.WriteLine("CA: {0,10:N2}", ca);
        }

    }
}
