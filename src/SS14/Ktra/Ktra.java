package SS14.Ktra;

import java.sql.*;

public class Ktra {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    public static void main(String[] args) {
        transfer("ACC01", "ACC02", 1000);
    }

    public static void transfer(String fromId, String toId, double amount) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            conn.setAutoCommit(false);

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

            String callSQL = "{call sp_UpdateBalance(?, ?)}";
            try (CallableStatement cs = conn.prepareCall(callSQL)) {

                cs.setString(1, fromId);
                cs.setDouble(2, -amount);
                cs.execute();

                cs.setString(1, toId);
                cs.setDouble(2, amount);
                cs.execute();
            }

            conn.commit();
            System.out.println("Chuyển khoản thành công!");

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
