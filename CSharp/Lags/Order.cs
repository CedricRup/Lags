using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lags
{
    class Order
    {
        public Order(String id, int start, int duration, double price)
        {
            this.id = id;
            this.Start = start;  // YYYYDDD format ex: 25 feb 2015 = 2015056
            this.Duration = duration;
            this.Price = price;
        }

        // getters and setters
        //oreder id 
        public string id { get; set; }
        // start
        public int Start { get; set; }
        // end
        public int Duration { get; set; }
        // value
        public double Price { get; set; }

    }
}
