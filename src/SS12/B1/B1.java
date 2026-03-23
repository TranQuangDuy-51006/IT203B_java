package SS12.B1;

import java.sql.*;

/*
 * ===== PHẦN 1 - PHÂN TÍCH (NGẮN GỌN) =====
 *
 * - SQL Injection xảy ra khi nối chuỗi trực tiếp vào câu SQL.
 *   Ví dụ: password = ' OR '1'='1 → luôn đúng → bypass login.
 *
 * - PreparedStatement là "tấm khiên" vì:
 *   + SQL được BIÊN DỊCH TRƯỚC (pre-compiled) với dấu ?
 *   + Dữ liệu truyền vào bằng setString() KHÔNG làm thay đổi cấu trúc SQL
 *   + Input chỉ được coi là dữ liệu, không phải câu lệnh
 *
 * → Ngăn chặn hoàn toàn SQL Injection
 */

public class B1 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    // ===== PHẦN 2 - CODE AN TOÀN =====
    public static boolean login(String doctorCode, String password) {
        String sql = "SELECT * FROM doctors WHERE doctor_code = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Gán giá trị vào dấu ?
            ps.setString(1, doctorCode);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            // Có dữ liệu → login thành công
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {

        // Test SQL Injection (sẽ FAIL nếu an toàn)
        String doctorCode = "D001";
        String password = "' OR '1'='1";

        if (login(doctorCode, password)) {
            System.out.println("Đăng nhập thành công");
        } else {
            System.out.println("Sai tài khoản hoặc mật khẩu");
        }
    }
}
