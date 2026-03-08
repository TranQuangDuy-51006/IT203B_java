package SS1.B6;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        User user = new User();

        try {

            // ===== Bài 1: Nhập năm sinh =====
            System.out.print("Nhập năm sinh: ");
            String birthInput = scanner.nextLine();

            int birthYear = Integer.parseInt(birthInput);
            int age = 2026 - birthYear;

            user.setAge(age);

            // ===== Defensive Programming =====
            if (user != null) {
                System.out.println("Tuổi người dùng: " + user.getAge());
            }

            // ===== Bài 2: Chia nhóm =====
            System.out.print("Nhập tổng số người: ");
            int totalUsers = scanner.nextInt();

            System.out.print("Nhập số nhóm: ");
            int groups = scanner.nextInt();

            int result = totalUsers / groups;
            System.out.println("Mỗi nhóm có: " + result + " người");

            // ===== Bài 4: Ghi file =====
            processUserData();

        }
        catch (NumberFormatException e) {
            logError("Sai định dạng số", e);
        }
        catch (ArithmeticException e) {
            logError("Không thể chia cho 0", e);
        }
        catch (InvalidAgeException e) {
            logError("Lỗi nghiệp vụ tuổi", e);
        }
        catch (IOException e) {
            logError("Lỗi ghi file", e);
        }
        finally {
            scanner.close();
            System.out.println("Thực hiện dọn dẹp tài nguyên trong finally...");
        }

        System.out.println("Chương trình kết thúc an toàn.");
    }

    // ===== Method B =====
    public static void processUserData() throws IOException {
        System.out.println("Đang xử lý dữ liệu người dùng...");
        saveToFile();
    }

    // ===== Method C =====
    public static void saveToFile() throws IOException {
        System.out.println("Đang lưu dữ liệu vào file...");
        throw new IOException("Không thể ghi file.");
    }

    // ===== Giả lập hệ thống Log =====
    public static void logError(String message, Exception e) {

        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("[ERROR] " + time + " - " + message);
        System.out.println("Chi tiết: " + e.getMessage());
    }
}


// ===== User Class =====
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


// ===== Custom Exception =====
class InvalidAgeException extends Exception {

    public InvalidAgeException(String msg) {
        super(msg);
    }

}