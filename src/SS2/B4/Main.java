package SS2.B4;

import java.util.*;
import java.util.function.*;

class User {
    private String username;

    public User() {
        this.username = "defaultUser";
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

public class Main {
    public static void main(String[] args) {

        List<User> users = Arrays.asList(
                new User("duy"),
                new User("admin"),
                new User("guest")
        );

        Function<User, String> getUsername = User::getUsername;

        Consumer<String> print = System.out::println;

        Supplier<User> createUser = User::new;

        users.stream()
                .map(getUsername)
                .forEach(print);

        User newUser = createUser.get();
        System.out.println(newUser.getUsername());
    }
}