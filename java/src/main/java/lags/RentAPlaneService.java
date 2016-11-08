package lags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RentAPlaneService {

    private List<Order> orders = new ArrayList<>();
    private static final Logger LOG = LoggerFactory.getLogger(RentAPlaneService.class);

    public void loadOrdersFromFile(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] champs = line.split(";");
                String fld1 = champs[0];
                int fld2 = Integer.parseInt(champs[1]);
                int filed3 = Integer.parseInt(champs[2]);
                double fld4 = Double.parseDouble(champs[3]);
                Order order = new Order(fld1, fld2, filed3, fld4);
                orders.add(order);
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            // TODO WTF ...
            LOG.info("CSV FILE NOT FOUND; CREATING ONE.");
            writeOrdersToFile(fileName);
        } catch (IOException e) {
            //FIXME don't catch but let API user handle this?
        }
    }

    private void writeOrdersToFile(String filename) {
        try {
            FileWriter writer = new FileWriter(new File(filename));
            for (Order order : orders) {
                String[] CSVline = new String[4];
                CSVline[0] = order.getId();
                CSVline[1] = Integer.toString(order.getDepartureDateYYYYDD());
                CSVline[2] = Integer.toString(order.getDurationInDays());
                CSVline[3] = Double.toString(order.getPrice());
                writer.write(String.join(";", CSVline) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            // TODO CRU what do you want to do here ?
        }
    }

    public void addOrderAndWriteToFile(Order order) throws IOException {
        orders.add(order);
        writeOrdersToFile("..\\ordres.csv");
    }

    double calculateGrossSales(List<Order> orders) {
        if (orders.size() == 0) {
            return 0.0;
        }
        // TODO: Fix it really and don't throw an exception, see PLAF ticket nO 4807
        //validateOrdersDontSpanOverTwoYears(orders);
        Order firstOrder = orders.get(0);
        List<Order> allOrdersPossibleAfterFirstOrder = orders.stream()
            .filter(o -> o.getDepartureDateYYYYDD() >= (firstOrder.getDepartureDateYYYYDD() + firstOrder.getDurationInDays()))
            .collect(Collectors.toList());
        List<Order> orderListWithoutFirstOrder = orders.subList(1, orders.size());
        double grossSale1 = firstOrder.getPrice() + calculateGrossSales(allOrdersPossibleAfterFirstOrder);
        double grossSale2 = calculateGrossSales(orderListWithoutFirstOrder);
        double bestGrossSale = Math.max(grossSale1, grossSale2);
        LOG.debug(new DecimalFormat("#.##").format(bestGrossSale));
        return bestGrossSale;
    }

    public void removeOrderAndWriteToFile(String id) throws IOException {
        this.orders = orders.stream().filter(o -> !o.getId().equals(id.toUpperCase())).collect(Collectors.toList());
        writeOrdersToFile("..\\ORDRES.CSV");
    }

    public double calculateAndShowGrossSales() {
        return calculateGrossSales(getOrdersSortByDepartureDate());

    }

    public List<Order> getOrdersSortByDepartureDate() {
        return orders
            .stream()
            .sorted((o1, o2) -> Integer.compare(o1.getDepartureDateYYYYDD(), o2.getDepartureDateYYYYDD()))
            .collect(Collectors.toList());
    }

    public List<Order> getOrders() {
        return new ArrayList<Order>(orders);
    }

}
