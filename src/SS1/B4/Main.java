package SS1.B4;

import java.io.IOException;

public class Main {

    // Method C
    public static void saveToFile() throws IOException {
        System.out.println("Đang lưu dữ liệu vào file...");

        // Giả lập lỗi ghi file
        throw new IOException("Lỗi khi ghi file!");
    }

    // Method B
    public static void processUserData() throws IOException {
        System.out.println("Đang xử lý dữ liệu người dùng...");

        // Gọi Method C và đẩy trách nhiệm lên trên
        saveToFile();
    }

    // Method A
    public static void main(String[] args) {

        try {
            processUserData();
        }
        catch (IOException e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
    }
}