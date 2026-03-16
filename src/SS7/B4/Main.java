package SS7.B4;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Cấu hình 1: File + Email
        OrderRepository repo1 = new FileOrderRepository();
        NotificationService notify1 = new EmailService();

        OrderService orderService1 = new OrderService(repo1, notify1);

        Order order1 = new Order("ORD001");
        orderService1.createOrder(order1);

        System.out.println();

        // Cấu hình 2: Database + SMS
        OrderRepository repo2 = new DatabaseOrderRepository();
        NotificationService notify2 = new SMSNotification();

        OrderService orderService2 = new OrderService(repo2, notify2);

        Order order2 = new Order("ORD002");
        orderService2.createOrder(order2);
    }
}

/* ===== Model ===== */

class Order {

    private String id;

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

/* ===== Interfaces (Abstraction) ===== */

interface OrderRepository {

    void save(Order order);

    List<Order> findAll();
}

interface NotificationService {

    void send(String message, String recipient);
}

/* ===== Implementations ===== */

class FileOrderRepository implements OrderRepository {

    public void save(Order order) {
        System.out.println("Lưu đơn hàng vào file: " + order.getId());
    }

    public List<Order> findAll() {
        return new ArrayList<>();
    }
}

class DatabaseOrderRepository implements OrderRepository {

    public void save(Order order) {
        System.out.println("Lưu đơn hàng vào database: " + order.getId());
    }

    public List<Order> findAll() {
        return new ArrayList<>();
    }
}

class EmailService implements NotificationService {

    public void send(String message, String recipient) {
        System.out.println("Gửi email: " + message);
    }
}

class SMSNotification implements NotificationService {

    public void send(String message, String recipient) {
        System.out.println("Gửi SMS: " + message);
    }
}

/* ===== Service (High-level module) ===== */

class OrderService {

    private OrderRepository orderRepository;
    private NotificationService notificationService;

    public OrderService(OrderRepository orderRepository,
                        NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    public void createOrder(Order order) {

        orderRepository.save(order);

        notificationService.send(
                "Đơn hàng " + order.getId() + " đã được tạo",
                "customer"
        );
    }
}