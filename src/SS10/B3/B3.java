package SS10.B3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class B3 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    public static void main(String[] args) {
        updateBedStatus("Bed_999"); // test mã không tồn tại
    }

    public static void updateBedStatus(String bedId) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "UPDATE beds SET bed_status = 'Đang sử dụng' WHERE bed_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, bedId);

            int rowsAffected = ps.executeUpdate();


            if (rowsAffected == 0) {
                System.out.println(" Mã giường " + bedId + " không tồn tại!");
            } else {
                System.out.println(" Cập nhật trạng thái giường thành công!");
            }

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
