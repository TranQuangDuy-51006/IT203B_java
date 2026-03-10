package SS3.B1;

import java.util.List;

record User(String username, String email, String status) {
}

public class Main {
    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alice", "alice@example.com", "ACTIVE"),
                new User("bob", "bob@example.com", "INACTIVE"),
                new User("charlie", "charlie@example.com", "ACTIVE")
        );

        users.forEach(user -> System.out.println(user));
    }
}