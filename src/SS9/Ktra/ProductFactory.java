package SS9.Ktra;


import java.util.Scanner;

public class ProductFactory {
    public static Product createProduct(int type, Scanner sc){
        System.out.println("Nhập id: ");
        String id = sc.nextLine();
        System.out.println("Nhập name: ");
        String name = sc.nextLine();
        System.out.println("Nhập price: ");
        double price = Double.parseDouble(sc.nextLine());
        if(type == 1){
            System.out.println("Nhập weight: ");
            double weight = Double.parseDouble(sc.nextLine());
            return new PhysicalProduct(id, name, price, weight);
        }else if(type == 2){
            System.out.println("Nhập size: ");
            double size = Double.parseDouble(sc.nextLine());
            return new DigitalProduct(id, name, price, size);
        }
        return null;
    }
}
