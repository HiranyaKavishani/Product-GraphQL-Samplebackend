package test.product.graphql;

public class Customer {

    private String name;
    private String id;

    Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setId(String id) {

        this.id = id;
    }

}
