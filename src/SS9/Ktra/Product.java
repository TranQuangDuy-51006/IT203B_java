package SS9.Ktra;

abstract class Product {
    protected String id;
    protected String name;
    protected double price;

    public Product() {
    }

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public abstract void displayInfo();
}


