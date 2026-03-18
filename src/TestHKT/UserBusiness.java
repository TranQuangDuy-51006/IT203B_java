package TestHKT;

import java.util.*;
import java.util.stream.Collectors;

public class UserBusiness {
    private static UserBusiness instance;
    private List<User> userList;

    private UserBusiness() {
        userList = new ArrayList<>();
    }

    public static UserBusiness getInstance() {
        if (instance == null) {
            instance = new UserBusiness();
        }
        return instance;
    }

    // Hiển thị
    public void displayAll() {
        if (userList.isEmpty()) {
            System.out.println("Danh sách rỗng!");
            return;
        }

        System.out.println("------------------------------------------------------");
        userList.forEach(User::displayData);
    }

    // Thêm
    public void addUser(User user) {
        boolean exists = userList.stream()
                .anyMatch(u -> u.getUserId().equals(user.getUserId()));

        if (exists) {
            System.out.println("Mã người dùng đã tồn tại");
        } else {
            userList.add(user);
        }
    }

    // Tìm theo ID
    public Optional<User> findById(String id) {
        return userList.stream()
                .filter(u -> u.getUserId().equals(id))
                .findFirst();
    }

    // Update
    public void updateUser(String id, Scanner sc) {
        Optional<User> opt = findById(id);

        if (!opt.isPresent()) {
            System.out.println("Mã người dùng không tồn tại trong hệ thống");
            return;
        }

        User user = opt.get();

        System.out.println("1. Tên");
        System.out.println("2. Tuổi");
        System.out.println("3. Role");
        System.out.println("4. Score");

        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1:
                System.out.print("Tên mới: ");
                user.setUserName(sc.nextLine());
                break;
            case 2:
                System.out.print("Tuổi mới: ");
                user.setAge(Integer.parseInt(sc.nextLine()));
                break;
            case 3:
                System.out.print("Role mới: ");
                user.setRole(sc.nextLine());
                break;
            case 4:
                System.out.print("Score mới: ");
                user.setScore(Double.parseDouble(sc.nextLine()));
                break;
        }
    }

    // Xóa
    public void deleteUser(String id) {
        int sizeBefore = userList.size();
        userList.removeIf(u -> u.getUserId().equals(id));

        if (sizeBefore == userList.size()) {
            System.out.println("Mã người dùng không tồn tại trong hệ thống");
        }
    }

    // Tìm theo tên
    public void searchByName(String name) {
        List<User> result = userList.stream()
                .filter(u -> u.getUserName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("Không tìm thấy!");
        } else {
            result.forEach(User::displayData);
            System.out.println("Tổng: " + result.size());
        }
    }

    // Lọc ADMIN
    public void filterAdmin() {
        userList.stream()
                .filter(u -> u.getRole().equals("ADMIN"))
                .forEach(User::displayData);
    }

    // Sắp xếp
    public void sortByScoreDesc() {
        userList = userList.stream()
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                .collect(Collectors.toList());

        displayAll();
    }
}
