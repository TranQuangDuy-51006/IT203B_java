package SS10.B4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class B4 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        searchPatient("Nguyen Van A"); // test
    }

    public static void searchPatient(String input) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);


            input = input.replace("--", "")
                    .replace(";", "")
                    .replace("'", "");

            String sql = "SELECT * FROM patients WHERE name = '" + input + "'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            System.out.println("Kết quả tìm kiếm:");

            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
