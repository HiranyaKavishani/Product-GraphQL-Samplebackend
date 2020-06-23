package test.product.graphql;

import java.util.List;

public class Product {

    private String id;
    private String name;
    private List<Customer> customer;


    Product(String id, String name, List<Customer> customer) {
        this.id = id;
        this.name = name;
        this.customer = customer;
    }

    public void setId(String id) {
        this.id = id;
    }

    private void setName(String name) {

        this.name = name;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }



}
