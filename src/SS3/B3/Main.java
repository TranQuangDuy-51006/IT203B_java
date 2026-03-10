package SS3.B3;

import java.util.List;
import java.util.Optional;

record User(String username, String email, String status) {}

class UserRepository {

    private List<User> users = List.of(
            new User("alice", "alice@gmail.com", "ACTIVE"),
            new User("bob", "bob@yahoo.com", "INACTIVE"),
            new User("charlie", "charlie@gmail.com", "ACTIVE")
    );

    public Optional<User> findUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.username().equals(username))
                .findFirst();
    }
}

public class Main {

    public static void main(String[] args) {

        UserRepository repo = new UserRepository();

        Optional<User> user = repo.findUserByUsername("alice");

        user.ifPresentOrElse(
                u -> System.out.println("Welcome " + u.username()),
                () -> System.out.println("Guest login")
        );
    }
}