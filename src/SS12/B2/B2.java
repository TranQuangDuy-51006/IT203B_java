package SS12.B2;

import java.sql.*;

/*
 * ===== PHẦN 1 - PHÂN TÍCH (NGẮN GỌN) =====
 *
 * - Khi dùng Statement + nối chuỗi:
 *   double temp = 37.5 hoặc 37,5 (tùy Locale hệ điều hành)
 *   → nếu dùng dấu phẩy (37,5) → SQL bị lỗi cú pháp
 *
 * - PreparedStatement giải quyết vì:
 *   + Dữ liệu KHÔNG nối vào SQL mà truyền riêng
 *   + setDouble(), setInt() gửi giá trị theo đúng kiểu dữ liệu (binary)
 *   + DB tự hiểu đúng kiểu số → KHÔNG phụ thuộc dấu chấm/phẩy
 *
 * → Không lỗi định dạng + an toàn + chuẩn kiểu dữ liệu
 */

public class B2 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    public static void updateVitalSigns(String patientId, double temperature, int heartRate) {
        String sql = "UPDATE patients SET temperature = ?, heart_rate = ? WHERE patient_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Gán đúng kiểu dữ liệu
            ps.setDouble(1, temperature); // không lo 37.5 hay 37,5
            ps.setInt(2, heartRate);
            ps.setString(3, patientId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Cập nhật thành công");
            } else {
                System.out.println("Không tìm thấy bệnh nhân");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // Test dữ liệu
        String patientId = "P001";
        double temperature = 37.5; // dù hệ thống locale khác vẫn OK
        int heartRate = 80;

        updateVitalSigns(patientId, temperature, heartRate);
    }
}