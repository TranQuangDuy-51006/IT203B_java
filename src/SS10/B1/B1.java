package SS10.B1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class B1 {


    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";


    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static void getPatients() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "SELECT * FROM patients";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            System.out.println("Danh sách bệnh nhân:");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                System.out.println(id + " - " + name);
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

    // ====== MAIN TEST ======
    public static void main(String[] args) {
        getPatients();
    }
}