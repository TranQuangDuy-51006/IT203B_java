package SS13.B3;

import java.sql.*;

public class B3 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    public static void main(String[] args) {
        xuatVienVaThanhToan(1, 500.0);
    }

    public static void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        PreparedStatement psSelect = null;
        PreparedStatement psUpdateWallet = null;
        PreparedStatement psUpdateBed = null;
        PreparedStatement psUpdatePatient = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);

            // Lấy số dư
            String sqlSelect = "SELECT balance FROM Patient_Wallet WHERE patient_id = ?";
            psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setInt(1, maBenhNhan);
            rs = psSelect.executeQuery();

            if (!rs.next()) {
                throw new Exception("Bệnh nhân không tồn tại");
            }

            double balance = rs.getDouble("balance");

            // ===== BẪY 1: Kiểm tra đủ tiền =====
            if (balance < tienVienPhi) {
                throw new Exception("Không đủ tiền thanh toán");
            }

            // Trừ tiền
            String sql1 = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            psUpdateWallet = conn.prepareStatement(sql1);
            psUpdateWallet.setDouble(1, tienVienPhi);
            psUpdateWallet.setInt(2, maBenhNhan);
            int row1 = psUpdateWallet.executeUpdate();

            // ===== BẪY 2: Row affected =====
            if (row1 == 0) {
                throw new Exception("Trừ tiền thất bại");
            }

            // Giải phóng giường
            String sql2 = "UPDATE Beds SET status = 'TRONG' WHERE patient_id = ?";
            psUpdateBed = conn.prepareStatement(sql2);
            psUpdateBed.setInt(1, maBenhNhan);
            int row2 = psUpdateBed.executeUpdate();

            // ===== BẪY 2 =====
            if (row2 == 0) {
                throw new Exception("Cập nhật giường thất bại");
            }

            // Cập nhật trạng thái bệnh nhân
            String sql3 = "UPDATE Patients SET status = 'XUAT_VIEN' WHERE patient_id = ?";
            psUpdatePatient = conn.prepareStatement(sql3);
            psUpdatePatient.setInt(1, maBenhNhan);
            int row3 = psUpdatePatient.executeUpdate();

            // ===== BẪY 2 =====
            if (row3 == 0) {
                throw new Exception("Cập nhật bệnh nhân thất bại");
            }

            conn.commit();
            System.out.println("Xuất viện và thanh toán thành công!");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (psSelect != null) psSelect.close();
                if (psUpdateWallet != null) psUpdateWallet.close();
                if (psUpdateBed != null) psUpdateBed.close();
                if (psUpdatePatient != null) psUpdatePatient.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}