package SS10.B2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class B2 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    public static void main(String[] args) {
        printMedicines();
    }

    public static void printMedicines() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT name, quantity FROM medicines";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            System.out.println("Danh mục thuốc:");

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;

                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");

                System.out.println("Tên thuốc: " + name + " | Số lượng: " + quantity);
            }


            if (!hasData) {
                System.out.println("Không có thuốc nào trong kho.");
            }

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
