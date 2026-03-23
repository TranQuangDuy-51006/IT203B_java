package SS12.B3;

import java.sql.*;

/*
 * ===== PHẦN 1 - PHÂN TÍCH (NGẮN GỌN) =====
 *
 * - Với Stored Procedure có OUT parameter:
 *   JDBC KHÔNG tự biết kiểu dữ liệu trả về → phải khai báo trước bằng registerOutParameter()
 *
 * - Nếu không gọi:
 *   → Lỗi: "The column index is out of range"
 *   → Hoặc không lấy được giá trị OUT
 *
 * - Lý do:
 *   + Driver cần biết vị trí & kiểu dữ liệu để nhận kết quả từ DB
 *
 * - Nếu SQL dùng kiểu DECIMAL:
 *   → Java phải dùng: Types.DECIMAL
 *
 * → Quy trình đúng:
 *   1. prepareCall()
 *   2. set IN parameter
 *   3. registerOutParameter()
 *   4. execute()
 *   5. get giá trị OUT
 */

public class B3 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    // ===== PHẦN 2 - CODE =====
    public static void getSurgeryFee(int surgeryId) {

        // Cú pháp gọi Stored Procedure
        String sql = "{ call GET_SURGERY_FEE(?, ?) }";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            // 1. Gán IN parameter
            cs.setInt(1, surgeryId);

            // 2. Đăng ký OUT parameter (DECIMAL)
            cs.registerOutParameter(2, Types.DECIMAL);

            // 3. Thực thi
            cs.execute();

            // 4. Lấy giá trị OUT
            double totalCost = cs.getDouble(2);

            System.out.println("Chi phí phẫu thuật: " + totalCost);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        int surgeryId = 101;

        getSurgeryFee(surgeryId);
    }
}
