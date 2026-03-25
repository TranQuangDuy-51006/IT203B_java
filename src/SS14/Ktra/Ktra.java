package SS14.Ktra;

import java.sql.*;

public class TransferMoneyFull {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        transfer("ACC01", "ACC02", 1000);
    }

    public static void transfer(String fromId, String toId, double amount) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // 1. Transaction
            conn.setAutoCommit(false);

            // 2. Kiểm tra tài khoản + số dư
            String checkSQL = "SELECT Balance FROM Accounts WHERE AccountId = ?";
            try (PreparedStatement ps = conn.prepareStatement(checkSQL)) {

                ps.setString(1, fromId);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    throw new Exception("Tài khoản gửi không tồn tại");
                }

                double balance = rs.getDouble("Balance");

                if (balance < amount) {
                    throw new Exception("Không đủ tiền");
                }
            }

            // 3. Gọi Stored Procedure
            String callSQL = "{call sp_UpdateBalance(?, ?)}";
            try (CallableStatement cs = conn.prepareCall(callSQL)) {

                // Trừ tiền
                cs.setString(1, fromId);
                cs.setDouble(2, -amount);
                cs.execute();

                // Cộng tiền
                cs.setString(1, toId);
                cs.setDouble(2, amount);
                cs.execute();
            }

            // 4. Commit
            conn.commit();
            System.out.println("Chuyển khoản thành công!");

            // 5. In kết quả cuối (ăn 10đ)
            String resultSQL = "SELECT * FROM Accounts WHERE AccountId IN (?, ?)";

            try (PreparedStatement ps2 = conn.prepareStatement(resultSQL)) {
                ps2.setString(1, fromId);
                ps2.setString(2, toId);

                ResultSet rs2 = ps2.executeQuery();

                System.out.println("===== KẾT QUẢ SAU CHUYỂN =====");
                while (rs2.next()) {
                    System.out.println(
                            rs2.getString("AccountId") + " | " +
                            rs2.getString("FullName") + " | " +
                            rs2.getDouble("Balance")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
