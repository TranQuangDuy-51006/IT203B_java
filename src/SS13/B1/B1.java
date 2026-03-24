package SS13.B1;

// PHẦN 1 - GIẢI THÍCH:
// Trong JDBC, mặc định Auto-Commit = true, nghĩa là mỗi câu lệnh SQL sẽ được commit ngay sau khi thực thi.
// Khi lệnh UPDATE trừ thuốc trong kho chạy thành công, dữ liệu đã được commit ngay lập tức.
// Nếu lệnh INSERT lưu lịch sử phía sau bị lỗi (do mất mạng, lỗi hệ thống...),
// thì không thể rollback lại lệnh UPDATE trước đó.
// Kết quả là kho vẫn bị trừ thuốc nhưng không có lịch sử cấp phát,
// gây vi phạm tính Atomicity (tính nguyên tử) của Transaction.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class B1 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    public static void main(String[] args) {
        int medicineId = 1;
        int patientId = 101;

        dispenseMedicine(medicineId, patientId);
    }

    public static void dispenseMedicine(int medicineId, int patientId) {
        Connection conn = null;
        PreparedStatement updateStock = null;
        PreparedStatement insertHistory = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            conn.setAutoCommit(false);

            String sql1 = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
            updateStock = conn.prepareStatement(sql1);
            updateStock.setInt(1, medicineId);
            updateStock.executeUpdate();

            String sql2 = "INSERT INTO Prescription_History(medicine_id, patient_id, date) VALUES (?, ?, NOW())";
            insertHistory = conn.prepareStatement(sql2);
            insertHistory.setInt(1, medicineId);
            insertHistory.setInt(2, patientId);
            insertHistory.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (updateStock != null) updateStock.close();
                if (insertHistory != null) insertHistory.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
