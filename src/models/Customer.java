package models;

public class Customer extends User {
    private String address;
    private int loyaltyPoints;
    
    public Customer(String userId, String name, String email,
                    String password, String phone, String address) {
        super(userId, name, email, password, phone);
        this.address = address;
        this.loyaltyPoints = 0;
    }
    
    @Override
    public void login() {
        System.out.println("Customer " + name + " logged in successfully!");
    }
    
    @Override
    public void logout() {
        System.out.println("Customer " + name + " logged out.");
    }
    
    // Getters and setters
    public String getAddress() { return address; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }
    
    public void displayCustomerInfo() {
        System.out.println("\n=== CUSTOMER INFORMATION ===");
        displayUserInfo();
        System.out.println("Address: " + address);
        System.out.println("Loyalty Points: " + loyaltyPoints);
    }


}