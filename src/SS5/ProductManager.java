package SS5;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManager {

    private List<Product> products = new ArrayList<>();

    // CREATE
    public void addProduct(Product product) throws InvalidProductException {

        boolean exists = products.stream()
                .anyMatch(p -> p.getId() == product.getId());

        if (exists) {
            throw new InvalidProductException("ID đã tồn tại!");
        }

        products.add(product);
    }

    // READ
    public void displayProducts() {

        if (products.isEmpty()) {
            System.out.println("Danh sách trống");
            return;
        }

        System.out.println("ID | Name | Price | Quantity | Category");

        products.forEach(p ->
                System.out.println(
                        p.getId() + " | "
                                + p.getName() + " | "
                                + p.getPrice() + " | "
                                + p.getQuantity() + " | "
                                + p.getCategory()
                )
        );
    }

    // UPDATE
    public void updateQuantity(int id, int quantity) throws InvalidProductException {

        Optional<Product> product = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (product.isPresent()) {
            product.get().setQuantity(quantity);
        } else {
            throw new InvalidProductException("Không tìm thấy sản phẩm!");
        }
    }

    // DELETE
    public void removeOutOfStock() {

        products.removeIf(p -> p.getQuantity() == 0);

    }
}
