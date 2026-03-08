package SS1.B2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập tổng số người dùng: ");
        int totalUsers = scanner.nextInt();

        System.out.print("Nhập số nhóm muốn chia: ");
        int groups = scanner.nextInt();

        try {
            int result = totalUsers / groups;
            System.out.println("Mỗi nhóm có: " + result + " người");
        }
        catch (ArithmeticException e) {
            System.out.println("Không thể chia cho 0!");
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
        scanner.close();
    }
}
