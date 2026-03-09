package SS2.B1;

import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {

        User user = new User("duy", "USER");
        User admin = new User("admin", "ADMIN");

        // Predicate: dùng để kiểm tra điều kiện và trả về true/false
        Predicate<User> isAdmin = u -> u.getRole().equals("ADMIN");

        System.out.println("User is admin: " + isAdmin.test(user));
        System.out.println("Admin is admin: " + isAdmin.test(admin));

        // Function: dùng để chuyển đổi dữ liệu từ kiểu này sang kiểu khác
        // ở đây chuyển từ User -> String (username)
        Function<User, String> getUsername = u -> u.getUsername();

        System.out.println("Username: " + getUsername.apply(user));

        // Consumer: dùng để thực hiện hành động với dữ liệu nhưng không trả về kết quả
        // ở đây là in thông tin user ra console
        Consumer<User> printUser = u -> System.out.println("User info: " + u);

        printUser.accept(user);

        // Supplier: dùng để tạo / cung cấp một đối tượng mà không cần tham số đầu vào
        Supplier<User> createDefaultUser = () -> new User("guest", "USER");

        User newUser = createDefaultUser.get();
        System.out.println("New user: " + newUser);
    }
}

// Class User đơn giản để test
class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Role: " + role;
    }
}
