package SS7.B1;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Product p1 = new Product("SP01", "Laptop", 15000000);
        Product p2 = new Product("SP02", "Chuột", 300000);

        System.out.println("Đã thêm sản phẩm SP01, SP02");

        Customer customer = new Customer("Nguyễn Văn A", "a@example.com", "Hà Nội");

        System.out.println("Đã thêm khách hàng");

        OrderItem item1 = new OrderItem(p1, 1);
        OrderItem item2 = new OrderItem(p2, 2);

        List<OrderItem> items = Arrays.asList(item1, item2);

        Order order = new Order("ORD001", customer, items);

        System.out.println("Đơn hàng ORD001 được tạo");

        OrderCalculator calculator = new OrderCalculator();
        double total = calculator.calculateTotal(order);
        order.setTotal(total);

        System.out.println("Tổng tiền: " + (long) total);

        OrderRepository repo = new OrderRepository();
        repo.save(order);

        EmailService email = new EmailService();
        email.sendEmail(customer.getEmail(),
                "Đơn hàng " + order.getOrderId() + " đã được tạo");
    }
}

class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

class Customer {
    private String name;
    private String email;
    private String address;

    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}

class Order {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private double total;

    public Order(String orderId, Customer customer, List<OrderItem> items) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

class OrderCalculator {

    public double calculateTotal(Order order) {
        double total = 0;

        for (OrderItem item : order.getItems()) {
            total += item.getTotalPrice();
        }

        return total;
    }
}

class OrderRepository {

    public void save(Order order) {
        System.out.println("Đã lưu đơn hàng " + order.getOrderId());
    }
}

class EmailService {

    public void sendEmail(String email, String message) {
        System.out.println("Đã gửi email đến " + email + ": " + message);
    }
}