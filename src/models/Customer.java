package models;

public class Customer extends Person {
    // Additional attribute specific to Customer
    private String email;
    private double totalSpent;
    
    public Customer(String id, String name, String phone, String email) {
        super(id, name, phone); // Call parent constructor
        this.email = email;
        this.totalSpent = 0.0;
    }
    
    // Getter and Setter
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public double getTotalSpent() {
        return totalSpent;
    }
    
    public void addToTotalSpent(double amount) {
        this.totalSpent += amount;
    }
    
    // Override displayInfo method (Polymorphism - will be used in Phase 3)
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Email: " + email);
        System.out.println("Total Spent: RM" + totalSpent);
    }
}