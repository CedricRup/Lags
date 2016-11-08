package lags;

public class Order {

    public Order(String id, int departureDateYYYYDD, int durationInDays, double price) {
        this.id = id;
        this.departureDateYYYYDD = departureDateYYYYDD;
        this.durationInDays = durationInDays;
        this.price = price;
    }

    private String id;
    /** YYYYDDD format example 25 feb 2015 = 2015056. */
    private int departureDateYYYYDD;
    private int durationInDays;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDepartureDateYYYYDD() {
        return departureDateYYYYDD;
    }

    public void setDepartureDateYYYYDD(int departureDateYYYYDD) {
        this.departureDateYYYYDD = departureDateYYYYDD;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + durationInDays;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + departureDateYYYYDD;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (durationInDays != other.durationInDays)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;
        if (departureDateYYYYDD != other.departureDateYYYYDD)
            return false;
        return true;
    }

}