package models;

public abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    protected String password;
    protected String phone;
    
    public User(String userId, String name, String email, 
                String password, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    
    // Abstract methods for polymorphism
    public abstract void login();
    public abstract void logout();
    
    // Getters (encapsulation)
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    
    // Login validation
    public boolean validateLogin(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
    public void displayUserInfo() {
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }
}