package SS13.B2;

// PHẦN 1 - GIẢI THÍCH:
// Khi đã dùng setAutoCommit(false), các câu lệnh SQL sẽ nằm trong một Transaction chưa được commit.
// Nếu xảy ra lỗi mà chỉ dùng System.out.println() để in ra lỗi thì Transaction vẫn chưa được kết thúc.
// Điều này vi phạm nguyên tắc của Transaction vì không đảm bảo dữ liệu được đưa về trạng thái nhất quán.
// Hành động bị thiếu là rollback() để hủy toàn bộ các thay đổi trước đó.
// Nếu không rollback, kết nối sẽ giữ Transaction dang dở (treo), gây lãng phí tài nguyên và ảnh hưởng đến các thao tác khác.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class B1 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    public static void main(String[] args) {
        int patientId = 1;
        int invoiceId = 1001;
        double amount = 500.0;

        payInvoice(patientId, invoiceId, amount);
    }

    public static void payInvoice(int patientId, int invoiceId, double amount) {
        Connection conn = null;
        PreparedStatement updateWallet = null;
        PreparedStatement updateInvoice = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            conn.setAutoCommit(false);

            String sql1 = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            updateWallet = conn.prepareStatement(sql1);
            updateWallet.setDouble(1, amount);
            updateWallet.setInt(2, patientId);
            updateWallet.executeUpdate();

            String sql2 = "UPDATE Invoices SET status = 'PAID' WHERE invoice_id = ?";
            updateInvoice = conn.prepareStatement(sql2);
            updateInvoice.setInt(1, invoiceId);
            updateInvoice.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (updateWallet != null) updateWallet.close();
                if (updateInvoice != null) updateInvoice.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}