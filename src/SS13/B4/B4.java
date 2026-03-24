package SS13.B4;

import java.sql.*;
import java.util.*;

class DichVu {
    int id;
    String name;

    public DichVu(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class BenhNhanDTO {
    int id;
    String name;
    List<DichVu> dsDichVu = new ArrayList<>();

    public BenhNhanDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class B4 {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "khanhkhanh";

    public static List<BenhNhanDTO> getDashboard() {
        List<BenhNhanDTO> result = new ArrayList<>();
        Map<Integer, BenhNhanDTO> map = new LinkedHashMap<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "SELECT bn.id, bn.name, dv.id AS dv_id, dv.name AS dv_name " +
                    "FROM BenhNhan bn " +
                    "LEFT JOIN DichVuSuDung dv ON bn.id = dv.maBenhNhan";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int bnId = rs.getInt("id");

                // Nếu chưa có bệnh nhân trong map → tạo mới
                if (!map.containsKey(bnId)) {
                    BenhNhanDTO bn = new BenhNhanDTO(
                            bnId,
                            rs.getString("name")
                    );
                    map.put(bnId, bn);
                }

                BenhNhanDTO bn = map.get(bnId);

                int dvId = rs.getInt("dv_id");

                // ===== BẪY 2: LEFT JOIN có thể trả về NULL =====
                // Nếu bệnh nhân chưa có dịch vụ → dv_id sẽ = 0 (do getInt)
                // hoặc cần check thêm rs.wasNull()
                if (!rs.wasNull()) {
                    DichVu dv = new DichVu(
                            dvId,
                            rs.getString("dv_name")
                    );
                    bn.dsDichVu.add(dv);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        result.addAll(map.values());
        return result;
    }

    public static void main(String[] args) {
        List<BenhNhanDTO> list = getDashboard();
        System.out.println("Số bệnh nhân: " + list.size());
    }
}
