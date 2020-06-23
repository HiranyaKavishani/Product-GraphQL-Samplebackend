package test.product.graphql;

public class AllProduct {

    private String id;
    private String name;
    private String description;
    private String category;

    AllProduct(String id, String name, String category, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private void setName(String name) {

        this.name = name;
    }


}
