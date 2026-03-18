package TestHKT;

public class User {
    private String userID;
    private String userName;
    private int age;
    private String role;
    private double score;

    public User() {
    }

    public User(String userID, String userName, int age, String role, double score) {
        this.userID = userID;
        this.userName = userName;
        this.age = age;
        this.role = role;
        this.score = score;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

