package SS12.B5;

import java.sql.*;
import java.util.Scanner;

public class B5 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    private static Connection conn;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            while (true) {
                System.out.println("\n===== RIKKEI HOSPITAL MENU =====");
                System.out.println("1. Danh sách bệnh nhân");
                System.out.println("2. Tiếp nhận bệnh nhân mới");
                System.out.println("3. Cập nhật bệnh án");
                System.out.println("4. Xuất viện & Tính phí");
                System.out.println("5. Thoát");
                System.out.print("Chọn: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        listPatients();
                        break;
                    case 2:
                        addPatient();
                        break;
                    case 3:
                        updateDisease();
                        break;
                    case 4:
                        dischargePatient();
                        break;
                    case 5:
                        System.out.println("Thoát chương trình!");
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Hiển thị danh sách bệnh nhân
    public static void listPatients() {
        String sql = "SELECT * FROM patients";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- DANH SÁCH BỆNH NHÂN ---");
            while (rs.next()) {
                System.out.println(
                        rs.getString("patient_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("age") + " | " +
                                rs.getString("department")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Thêm bệnh nhân (PreparedStatement chống SQL Injection)
    public static void addPatient() {
        String sql = "INSERT INTO patients(patient_id, name, age, department, disease) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.print("Nhập mã BN: ");
            String id = sc.nextLine();

            System.out.print("Nhập tên: ");
            String name = sc.nextLine(); // xử lý được L'Oréal

            System.out.print("Nhập tuổi: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Nhập khoa: ");
            String dept = sc.nextLine();

            System.out.print("Nhập bệnh: ");
            String disease = sc.nextLine();

            ps.setString(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, dept);
            ps.setString(5, disease);

            ps.executeUpdate();

            System.out.println("Thêm bệnh nhân thành công!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Cập nhật bệnh án
    public static void updateDisease() {
        String sql = "UPDATE patients SET disease = ? WHERE patient_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.print("Nhập mã BN: ");
            String id = sc.nextLine();

            System.out.print("Nhập bệnh mới: ");
            String disease = sc.nextLine();

            ps.setString(1, disease);
            ps.setString(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy bệnh nhân!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Xuất viện & tính phí (CallableStatement + OUT)
    public static void dischargePatient() {
        String sql = "{ call CALCULATE_DISCHARGE_FEE(?, ?) }";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            System.out.print("Nhập mã BN: ");
            String id = sc.nextLine();

            // IN
            cs.setString(1, id);

            // OUT (giả sử DECIMAL)
            cs.registerOutParameter(2, Types.DECIMAL);

            cs.execute();

            double fee = cs.getDouble(2);

            System.out.println("Tổng viện phí: " + fee);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}