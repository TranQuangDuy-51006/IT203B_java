package SS9.Ktra;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductDatabase db = ProductDatabase.getInstance();

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Xem danh sách");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("1. Physical | 2. Digital");
                    int type = Integer.parseInt(sc.nextLine());
                    Product p = ProductFactory.createProduct(type, sc);
                    if (p != null) db.add(p);
                    break;

                case 2:
                    db.displayAll();
                    break;

                case 3:
                    System.out.print("Nhập ID cần xóa: ");
                    String id = sc.nextLine();
                    db.delete(id);
                    break;

                case 0:
                    
                    return;
            }
        }
    }
}
