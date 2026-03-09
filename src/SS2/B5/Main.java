package SS2.B5;

interface UserActions {
    default void logActivity(String activity) {
        System.out.println("User activity: " + activity);
    }
}

interface AdminActions {
    default void logActivity(String activity) {
        System.out.println("Admin activity: " + activity);
    }
}

class SuperAdmin implements UserActions, AdminActions {

    @Override
    public void logActivity(String activity) {
        UserActions.super.logActivity(activity);
        AdminActions.super.logActivity(activity);
    }
}

public class Main {
    public static void main(String[] args) {
        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.logActivity("Delete user account");
    }
}