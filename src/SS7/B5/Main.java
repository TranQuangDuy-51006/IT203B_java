package SS7.B5;

import java.util.*;


public class Main {

    static Scanner sc = new Scanner(System.in);

    static List<Product> products = new ArrayList<>();
    static List<Customer> customers = new ArrayList<>();
    static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) {

        OrderRepository repo = new FileOrderRepository();
        NotificationService notify = new EmailNotification();

        OrderService service = new OrderService(repo, notify);

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Thêm khách hàng");
            System.out.println("3. Tạo đơn hàng");
            System.out.println("4. Xem đơn hàng");
            System.out.println("5. Tính doanh thu");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> addProduct();
                case 2 -> addCustomer();
                case 3 -> createOrder(service);
                case 4 -> showOrders();
                case 5 -> revenue();
                case 0 -> System.exit(0);
            }
        }
    }

    static void addProduct() {

        System.out.print("Mã: ");
        String id = sc.nextLine();

        System.out.print("Tên: ");
        String name = sc.nextLine();

        System.out.print("Giá: ");
        double price = sc.nextDouble();
        sc.nextLine();

        products.add(new Product(id, name, price));

        System.out.println("Đã thêm sản phẩm " + id);
    }

    static void addCustomer() {

        System.out.print("Tên: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        customers.add(new Customer(name, email));

        System.out.println("Đã thêm khách hàng");
    }

    static void createOrder(OrderService service) {

        if (customers.isEmpty() || products.isEmpty()) {
            System.out.println("Chưa có dữ liệu");
            return;
        }

        Customer c = customers.get(0);
        Product p = products.get(0);

        OrderItem item = new OrderItem(p, 1);

        Order order = new Order("ORD00" + (orders.size() + 1), c);
        order.addItem(item);

        DiscountStrategy discount = new PercentageDiscount(10);
        PaymentMethod payment = new CreditCardPayment();

        service.processOrder(order, discount, payment);

        orders.add(order);
    }

    static void showOrders() {

        for (Order o : orders) {
            System.out.println(o.id + " - " + o.customer.name + " - " + (long)o.total);
        }
    }

    static void revenue() {

        double sum = 0;

        for (Order o : orders)
            sum += o.total;

        System.out.println("Tổng doanh thu: " + (long) sum);
    }
}

/* ================= MODELS ================= */

class Product {

    String id;
    String name;
    double price;

    Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class Customer {

    String name;
    String email;

    Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

class OrderItem {

    Product product;
    int qty;

    OrderItem(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }

    double getTotal() {
        return product.price * qty;
    }
}

class Order {

    String id;
    Customer customer;
    List<OrderItem> items = new ArrayList<>();
    double total;

    Order(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    void addItem(OrderItem item) {
        items.add(item);
    }
}

/* ================= DISCOUNT ================= */

interface DiscountStrategy {
    double apply(double total);
}

class PercentageDiscount implements DiscountStrategy {

    double percent;

    PercentageDiscount(double percent) {
        this.percent = percent;
    }

    public double apply(double total) {
        return total - total * percent / 100;
    }
}

class FixedDiscount implements DiscountStrategy {

    double amount;

    FixedDiscount(double amount) {
        this.amount = amount;
    }

    public double apply(double total) {
        return total - amount;
    }
}

class HolidayDiscount implements DiscountStrategy {

    public double apply(double total) {
        return total * 0.85;
    }
}

/* ================= PAYMENT ================= */

interface PaymentMethod {
    void pay(double amount);
}

class CODPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Thanh toán COD: " + (long)amount);
    }
}

class CreditCardPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Thanh toán thẻ tín dụng: " + (long)amount);
    }
}

class MomoPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Thanh toán MoMo: " + (long)amount);
    }
}

class VNPayPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Thanh toán VNPay: " + (long)amount);
    }
}

/* ================= REPOSITORY ================= */

interface OrderRepository {
    void save(Order order);
}

class FileOrderRepository implements OrderRepository {

    public void save(Order order) {
        System.out.println("Đã lưu đơn hàng " + order.id);
    }
}

class DatabaseOrderRepository implements OrderRepository {

    public void save(Order order) {
        System.out.println("Lưu DB: " + order.id);
    }
}

/* ================= NOTIFICATION ================= */

interface NotificationService {
    void send(String message, String email);
}

class EmailNotification implements NotificationService {

    public void send(String message, String email) {
        System.out.println("Đã gửi email: " + message);
    }
}

class SMSNotification implements NotificationService {

    public void send(String message, String phone) {
        System.out.println("Đã gửi SMS: " + message);
    }
}

/* ================= INVOICE ================= */

class InvoiceGenerator {

    static void print(Order order, double discountAmount) {

        System.out.println("\n=== HÓA ĐƠN ===");

        System.out.println("Khách: " + order.customer.name);

        for (OrderItem i : order.items) {

            System.out.println(
                    i.product.name +
                            " - SL: " + i.qty +
                            " - Giá: " + (long)i.product.price +
                            " - Thành tiền: " + (long)i.getTotal()
            );
        }

        System.out.println("Tổng tiền: " + (long)(order.total + discountAmount));
        System.out.println("Giảm giá: " + (long)discountAmount);
        System.out.println("Cần thanh toán: " + (long)order.total);
    }
}

/* ================= SERVICE ================= */

class OrderService {

    OrderRepository repo;
    NotificationService notify;

    OrderService(OrderRepository repo, NotificationService notify) {
        this.repo = repo;
        this.notify = notify;
    }

    void processOrder(Order order, DiscountStrategy discount, PaymentMethod payment) {

        double sum = 0;

        for (OrderItem i : order.items)
            sum += i.getTotal();

        double finalAmount = discount.apply(sum);
        double discountAmount = sum - finalAmount;

        order.total = finalAmount;

        InvoiceGenerator.print(order, discountAmount);

        payment.pay(finalAmount);

        repo.save(order);

        notify.send("Đơn hàng " + order.id + " đã được tạo", order.customer.email);
    }
}
