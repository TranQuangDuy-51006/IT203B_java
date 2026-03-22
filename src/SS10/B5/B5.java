package SS10.B5;

import java.sql.*;
import java.util.*;

public class B5 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    static class Doctor {
        String id, name, specialty;

        public Doctor(String id, String name, String specialty) {
            this.id = id;
            this.name = name;
            this.specialty = specialty;
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM doctors");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Doctor(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("specialty")
                ));
            }

        } catch (Exception e) {
            System.out.println("Loi DB: " + e.getMessage());
        }
        return list;
    }

    public static void insertDoctor(Doctor d) {
        String sql = "INSERT INTO doctors(id, name, specialty) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.id);
            ps.setString(2, d.name);
            ps.setString(3, d.specialty);

            ps.executeUpdate();
            System.out.println("Them bac si thanh cong");

        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void countBySpecialty() {
        String sql = "SELECT specialty, COUNT(*) AS total FROM doctors GROUP BY specialty";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getString("specialty") +
                                " | " +
                                rs.getInt("total")
                );
            }

        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public static void showDoctors() {
        List<Doctor> list = getAllDoctors();

        if (list.isEmpty()) {
            System.out.println("Khong co bac si");
            return;
        }

        for (Doctor d : list) {
            System.out.println(d.id + " | " + d.name + " | " + d.specialty);
        }
    }

    public static void addDoctor(Scanner sc) {
        System.out.print("Nhap ID: ");
        String id = sc.nextLine();

        System.out.print("Nhap ten: ");
        String name = sc.nextLine();

        System.out.print("Nhap chuyen khoa: ");
        String specialty = sc.nextLine();

        if (id.isEmpty() || name.isEmpty() || specialty.isEmpty()) {
            System.out.println("Du lieu khong hop le");
            return;
        }

        if (specialty.length() > 50) {
            System.out.println("Chuyen khoa qua dai");
            return;
        }

        insertDoctor(new Doctor(id, name, specialty));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("1. Xem danh sach bac si");
                System.out.println("2. Them bac si");
                System.out.println("3. Thong ke chuyen khoa");
                System.out.println("4. Thoat");
                System.out.print("Chon: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        showDoctors();
                        break;
                    case 2:
                        addDoctor(sc);
                        break;
                    case 3:
                        countBySpecialty();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Lua chon khong hop le");
                }

            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so");
            }
        }
    }
}