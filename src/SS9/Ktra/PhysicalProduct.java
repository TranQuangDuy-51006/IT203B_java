package SS9.Ktra;

public class PhysicalProduct extends Product{
    private double weight;


    public PhysicalProduct(String id, String name, double price, double weight) {
        super(id, name, price);
        this.weight = weight;
    }

    @Override
    public void displayInfo(){
        System.out.printf("Physical: ID: %s, Name: %s, Price: %f, Size: %f", id, name, price, weight);

    }
}
