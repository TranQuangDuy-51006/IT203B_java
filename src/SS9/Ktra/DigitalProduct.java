package SS9.Ktra;

public class DigitalProduct extends Product{
    private double size;


    public DigitalProduct(String id, String name, double price, double size) {
        super(id, name, price);
        this.size = size;
    }

    @Override
    public void displayInfo(){
        System.out.printf("Digital: ID: %s, Name: %s, Price: %f, Size: %f", id, name, price, size);
    }
}
