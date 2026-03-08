package SS1.B5;

public class Main {

    public static void main(String[] args) {

        User user = new User();

        try {
            user.setAge(-5);
            System.out.println("Tuổi: " + user.getAge());
        }
        catch (InvalidAgeException e) {
            System.out.println("Lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
    }
}

class User {

    private int age;

    public void setAge(int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Tuổi không thể âm!");
        }
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}

class InvalidAgeException extends Exception {

    public InvalidAgeException(String msg) {
        super(msg);
    }

}