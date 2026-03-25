package SS14.Ktra;

import java.sql.*;

// create a JDBC transaction with commit and rollback

public class Ktra {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    public static void main(String[] args) {

    }


    public void tranfer(String formId, String toId, double amount){
        try(Connection conn = DriverManager.getConnection(URL,USER,PASSWORD)){
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String
    }
}
