package SS9.Ktra;

import java.util.*;

public class ProductDatabase {
    private static ProductDatabase instance;
    private List<Product> products;
    private ProductDatabase(){
        products = new ArrayList<>();
    }

    public static ProductDatabase getInstance(){
        if(instance == null ){
            instance = new ProductDatabase();
        }
        return instance;
    }

    public void add(Product p){
        products.add(p);
    }

    public void displayAll(){
        for (Product p : products ){
            p.displayInfo();
        }
    }

    public Product findById(String id){
        for(Product p : products)
        {
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }

    public void delete(String id){
        products.removeIf(p->p.getId().equals(id));
    }

}
