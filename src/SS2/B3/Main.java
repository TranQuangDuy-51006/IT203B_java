package SS2.B3;

interface Authenticatable {

    String getPassword();

    default boolean isAuthenticated() {
        return getPassword() != null && !getPassword().isEmpty();
    }

    static String encrypt(String rawPassword) {
        return "ENC_" + rawPassword;
    }
}

class User implements Authenticatable {
    private String password;

    public User(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

public class Main {
    public static void main(String[] args) {
        User user = new User("123456");

        System.out.println(user.isAuthenticated());
        System.out.println(Authenticatable.encrypt("123456"));
    }
}