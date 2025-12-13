package views;

public class MenuView {
    
    public static void showMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Login to System");
        System.out.println("2. Register New Customer");
        System.out.println("3. Demonstrate Arrays (For Rubric)");
        System.out.println("4. Exit System");
        System.out.println("-".repeat(40));
        System.out.print("Select option (1-4): ");
    }
    
    public static void showLoginMenu() {
        System.out.println("\n=== LOGIN OPTIONS ===");
        System.out.println("1. Customer Login");
        System.out.println("2. Staff Login");
        System.out.println("3. Admin Login");
        System.out.println("4. Back to Main Menu");
        System.out.print("Select: ");
    }
    
    public static void showSectionHeader(String title) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        " + title);
        System.out.println("=".repeat(50));
    }
    
    public static void showSubSection(String title) {
        System.out.println("\n--- " + title + " ---");
    }
    
    public static void showMessage(String message) {
        System.out.println("\n" + message);
    }
    
    public static void showError(String error) {
        System.out.println("\n❌ ERROR: " + error);
    }
    
    public static void showSuccess(String success) {
        System.out.println("\n✅ " + success);
    }
    
    public static void showDivider() {
        System.out.println("-".repeat(40));
    }
    
    public static void showLoading(String message) {
        System.out.print("\n" + message);
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(300);
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            System.out.println();
        }
    }
}