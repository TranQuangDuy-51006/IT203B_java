package SS1.B3;

class User {
    private int age;

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Tuổi không thể âm!");
        }
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}

public class Main {
    public static void main(String[] args) {

        User user = new User();

        try {
            user.setAge(-5);
            System.out.println("Tuổi: " + user.getAge());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
    }
}
