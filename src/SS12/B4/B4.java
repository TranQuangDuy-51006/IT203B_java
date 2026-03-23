package SS12.B4;

import java.sql.*;
import java.util.*;

/*
 * ===== PHẦN 1 - PHÂN TÍCH (NGẮN GỌN) =====
 *
 * - Khi dùng Statement trong vòng lặp:
 *   → Mỗi lần insert = DB phải:
 *     1. Parse (phân tích cú pháp SQL)
 *     2. Tạo Execution Plan (kế hoạch thực thi)
 *
 * - Nếu chạy 1000 lần:
 *   → DB phải lặp lại 1000 lần parse + plan → cực kỳ tốn tài nguyên CPU
 *
 * - Lãng phí:
 *   + Cùng 1 câu SQL nhưng bị xử lý lại nhiều lần
 *   + Làm hệ thống chậm vào giờ cao điểm
 *
 * - PreparedStatement tối ưu vì:
 *   + SQL được compile 1 lần duy nhất
 *   + Tái sử dụng Execution Plan
 *   + Chỉ thay đổi dữ liệu đầu vào
 *
 * → Giảm mạnh thời gian xử lý và tải cho DB
 */

public class B4 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    public static void insertLabResults(List<String[]> dataList) {

        String sql = "INSERT INTO lab_results(patient_id, test_name, result_value) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            long start = System.currentTimeMillis();

            // Khởi tạo 1 lần, dùng trong vòng lặp
            for (String[] data : dataList) {
                ps.setString(1, data[0]); // patient_id
                ps.setString(2, data[1]); // test_name
                ps.setString(3, data[2]); // result_value

                ps.executeUpdate(); // chỉ thay dữ liệu, không parse lại SQL
            }

            long end = System.currentTimeMillis();

            System.out.println("Thời gian thực thi: " + (end - start) + " ms");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // Giả lập 1000 bản ghi
        List<String[]> dataList = new ArrayList<>();

        for (int i = 1; i <= 1000; i++) {
            dataList.add(new String[]{
                    "P" + i,
                    "Blood Test",
                    String.valueOf(4.5 + i % 10)
            });
        }

        insertLabResults(dataList);
    }
}