package models;

public class Staff extends User {
    private String role;
    private double salary;
    private boolean isAvailable;
    
    public Staff(String userId, String name, String email, 
                 String password, String phone, 
                 String role, double salary) {
        super(userId, name, email, password, phone);
        this.role = role;
        this.salary = salary;
        this.isAvailable = true;
    }
    
    @Override
    public void login() {
        System.out.println("Staff " + name + " logged in as " + role);
    }
    
    @Override
    public void logout() {
        System.out.println("Staff " + name + " logged out");
    }
    
    // Getters and setters
    public String getRole() { return role; }
    public double getSalary() { return salary; }
    public boolean isAvailable() { return isAvailable; }
    
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    
    public void displayStaffInfo() {
        System.out.println("\n=== STAFF INFORMATION ===");
        displayUserInfo();
        System.out.println("Role: " + role);
        System.out.println("Salary: RM" + salary);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
    }
}