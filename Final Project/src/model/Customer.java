package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static util.Flags.CUSTOMER_FILE;

public class Customer extends AbstractReadFile {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + SEPARATOR + name;
    }

    @Override
    public Customer readFromString(String s) {
        String[] profiles = s.split(SEPARATOR);
        Customer customer = new Customer();
        customer.setId(Integer.parseInt(profiles[0]));
        customer.setName(profiles[1]);
        return customer;
    }

    private static List<Customer> listCustomer() {
        try {
            Customer customer = new Customer();
            return customer.readFromFile(CUSTOMER_FILE).stream()
                    .map(customer::readFromString).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<String> getAllCusomterName() {
        return listCustomer().stream().map(per -> per.name).collect(Collectors.toList());
    }

    /**
     * set a new customer
     */
    public static void setNew(String name) throws IOException {
        List<Customer> allCustomer = listCustomer();
        int maxId = 0;
        List<Customer> customers = allCustomer.stream().filter(per -> name.equals(per.name)).collect(Collectors.toList());

        if (allCustomer.size() != 0) {
            maxId = allCustomer.stream().mapToInt(customer -> customer.id).max().getAsInt();
        }
        if (customers.size() == 1) {
        } else {
            Customer customer = new Customer();
            customer.name = name;
            customer.id = maxId + 1;
            allCustomer.add(customer);
            Customer.saveIntoFile(CUSTOMER_FILE, allCustomer.stream().map(Customer::toString).collect(Collectors.toList()));
        }
    }
}