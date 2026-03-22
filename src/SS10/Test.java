package SS10;

import java.sql.*;
import java.util.Scanner;

public class Test {
    private static Connection conn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            initConnection();
            while (true) {
                System.out.println("-------Menu-------");
                System.out.println("1. Hien thi danh sach benh nhan");
                System.out.println("2. Them benh nhan moi");
                System.out.println("3. Xoa benh nhan");
                System.out.println("0. Thoat");
                System.out.print("Nhap lua chon: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        displayPatients();
                        break;
                    case 2:
                        System.out.print("Nhap ten: ");
                        String fullName = sc.nextLine();

                        System.out.print("Nhap tuoi: ");
                        int age = Integer.parseInt(sc.nextLine());

                        System.out.print("Nhap chuan doan: ");
                        String diagnosis = sc.nextLine();

                        addPatient(fullName, age, diagnosis);
                        break;
                    case 3:
                        System.out.print("Nhap ID can xoa: ");
                        int id = Integer.parseInt(sc.nextLine());
                        deletePatient(id);
                        break;
                    case 0:
                        System.out.println("Thoat!");
                        return;
                    default:
                        System.out.println("Sai lua chon!");
                }
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    // READ
    public static void displayPatients() throws Exception {
        String sql = "SELECT * FROM patients";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.println(
                    "ID: " + rs.getInt("id") +
                            ", Ten: " + rs.getString("full_name") +
                            ", Tuoi: " + rs.getInt("age") +
                            ", Chan doan: " + rs.getString("diagnosis")
            );
        }

        rs.close();
        stmt.close();
    }

    // CREATE
    public static void addPatient(String fullName, int age, String diagnosis) throws Exception {
        String sql = "INSERT INTO patients (full_name, age, diagnosis) VALUES (?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, fullName);
        pstmt.setInt(2, age);
        pstmt.setString(3, diagnosis);

        int count = pstmt.executeUpdate();

        if (count > 0) {
            System.out.println("Them thanh cong!");
        } else {
            System.out.println("Them that bai!");
        }

        pstmt.close();
    }

    // DELETE
    public static void deletePatient(int id) throws Exception {
        String sql = "DELETE FROM patients WHERE id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);

        int count = pstmt.executeUpdate();

        if (count > 0) {
            System.out.println("Xoa thanh cong!");
        } else {
            System.out.println("Khong tim thay ID!");
        }

        pstmt.close();
    }

    // CONNECT
    public static void initConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "khanhkhanh";

        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Ket noi thanh cong!");
    }

    public static void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            System.out.println("Loi dong ket noi");
        }
    }
}