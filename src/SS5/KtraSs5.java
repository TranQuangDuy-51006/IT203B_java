package SS5;


import java.util.Scanner;

public class KtraSs5 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ProductManager manager = new ProductManager();

        while (true) {

            System.out.println("======= PRODUCT MANAGEMENT SYSTEM =======");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị sản phẩm");
            System.out.println("3. Cập nhật số lượng");
            System.out.println("4. Xóa sản phẩm hết hàng");
            System.out.println("5. Thoát");

            System.out.print("Chọn: ");
            int choice = sc.nextInt();

            try {

                switch (choice) {

                    case 1:

                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Name: ");
                        String name = sc.nextLine();

                        System.out.print("Price: ");
                        double price = sc.nextDouble();

                        System.out.print("Quantity: ");
                        int quantity = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Category: ");
                        String category = sc.nextLine();

                        Product p = new Product(id, name, price, quantity, category);

                        manager.addProduct(p);

                        System.out.println("Thêm thành công!");
                        break;

                    case 2:

                        manager.displayProducts();
                        break;

                    case 3:

                        System.out.print("ID cần cập nhật: ");
                        int updateId = sc.nextInt();

                        System.out.print("Quantity mới: ");
                        int newQuantity = sc.nextInt();

                        manager.updateQuantity(updateId, newQuantity);

                        System.out.println("Cập nhật thành công!");
                        break;

                    case 4:

                        manager.removeOutOfStock();
                        System.out.println("Đã xóa sản phẩm hết hàng!");
                        break;

                    case 5:

                        System.out.println("Thoát chương trình");
                        return;

                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }

            } catch (InvalidProductException e) {
                System.out.println("Lỗi: " + e.getMessage());
            }

        }
    }
                                   }
