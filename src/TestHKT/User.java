package TestHKT;

import java.util.Scanner;

public class User {
    private String userId;
    private String userName;
    private int age;
    private String role;
    private double score;

    // Constructor không tham số
    public User() {}

    // Constructor đầy đủ
    public User(String userId, String userName, int age, String role, double score) {
        this.userId = userId;
        this.userName = userName;
        this.age = age;
        this.role = role;
        this.score = score;
    }

    // Getter/Setter
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    // Input
    public void inputData(Scanner sc) {
        System.out.print("Nhập mã user: ");
        this.userId = sc.nextLine();

        System.out.print("Nhập tên: ");
        this.userName = sc.nextLine();

        while (true) {
            System.out.print("Nhập tuổi >=18: ");
            int age = Integer.parseInt(sc.nextLine());
            if (age >= 18) {
                this.age = age;
                break;
            }
            System.out.println("Tuổi không hợp lệ!");
        }

        while (true) {
            System.out.print("Nhập role (USER/ADMIN): ");
            String role = sc.nextLine().toUpperCase();
            if (role.equals("USER") || role.equals("ADMIN")) {
                this.role = role;
                break;
            }
            System.out.println("Role không hợp lệ!");
        }

        while (true) {
            System.out.print("Nhập score (0-10): ");
            double score = Double.parseDouble(sc.nextLine());
            if (score >= 0 && score <= 10) {
                this.score = score;
                break;
            }
            System.out.println("Score không hợp lệ!");
        }
    }

    // Display
    public void displayData() {
        System.out.printf("| %-5s | %-15s | %-3d | %-6s | %-5.2f |\n",
                userId, userName, age, role, score);
    }
}