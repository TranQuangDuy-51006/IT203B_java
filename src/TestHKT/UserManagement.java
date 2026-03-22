package TestHKT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class UserManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserBusiness business = UserBusiness.getInstance();

        while (true) {
            System.out.println("******** QUẢN LÝ NGƯỜI DÙNG ********");
            System.out.println("1. Hiển thị");
            System.out.println("2. Thêm");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa");
            System.out.println("5. Tìm kiếm");
            System.out.println("6. Lọc ADMIN");
            System.out.println("7. Sắp xếp score");
            System.out.println("8. Thoát");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    business.displayAll();
                    break;
                case 2:
                    User user = new User();
                    user.inputData(sc);
                    business.addUser(user);
                    break;
                case 3:
                    System.out.print("Nhập ID: ");
                    business.updateUser(sc.nextLine(), sc);
                    break;
                case 4:
                    System.out.print("Nhập ID: ");
                    business.deleteUser(sc.nextLine());
                    break;
                case 5:
                    System.out.print("Nhập tên: ");
                    business.searchByName(sc.nextLine());
                    break;
                case 6:
                    business.filterAdmin();
                    break;
                case 7:
                    business.sortByScoreDesc();
                    break;
                case 8:
                    System.exit(0);
            }
        }
    }
}