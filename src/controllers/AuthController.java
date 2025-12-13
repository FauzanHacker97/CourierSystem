package controllers;

import models.*;
import utils.Validator;
import java.util.*;

public class AuthController {
    private Scanner scanner;
    private ArrayList<User> users;
    
    public AuthController(Scanner scanner, ArrayList<User> users) {
        this.scanner = scanner;
        this.users = users;
    }
    
    public User login() {
        views.MenuView.showLoginMenu();
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: return customerLogin();
            case 2: return staffLogin();
            case 3: return adminLogin();
            case 4: return null;
            default: 
                views.MenuView.showError("Invalid choice!");
                return null;
        }
    }
    
    private Customer customerLogin() {
        views.MenuView.showSubSection("CUSTOMER LOGIN");
        System.out.print("Enter Customer ID (e.g., C001): ");
        String id = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        for (User user : users) {
            if (user instanceof Customer && 
                user.getUserId().equals(id) && 
                user.validateLogin(password)) {
                views.MenuView.showSuccess("Login successful! Welcome " + user.getName());
                return (Customer) user;
            }
        }
        views.MenuView.showError("Invalid customer ID or password!");
        return null;
    }
    
    private Staff staffLogin() {
        views.MenuView.showSubSection("STAFF LOGIN");
        System.out.print("Enter Staff ID (e.g., S001): ");
        String id = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        for (User user : users) {
            if (user instanceof Staff && 
                user.getUserId().equals(id) && 
                user.validateLogin(password)) {
                Staff staff = (Staff) user;
                views.MenuView.showSuccess("Login successful! Welcome " + staff.getName());
                staff.login(); // Polymorphic method call
                return staff;
            }
        }
        views.MenuView.showError("Invalid staff ID or password!");
        return null;
    }
    
    private User adminLogin() {
        views.MenuView.showSubSection("ADMIN LOGIN");
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();
        
        if (username.equals("admin") && password.equals("admin123")) {
            // Create admin user (Staff with admin role)
            Staff admin = new Staff("ADMIN", "System Administrator", 
                                  "admin@courier.com", "admin123", 
                                  "000-0000000", "System Admin", 0);
            views.MenuView.showSuccess("Admin login successful!");
            return admin;
        }
        views.MenuView.showError("Invalid admin credentials!");
        return null;
    }
    
    public Customer registerCustomer() {
        views.MenuView.showSectionHeader("REGISTER NEW CUSTOMER");
        
        System.out.print("Enter Customer ID (e.g., C004): ");
        String id = scanner.nextLine();
        
        // Check if ID already exists
        for (User user : users) {
            if (user.getUserId().equals(id)) {
                views.MenuView.showError("Customer ID already exists!");
                return null;
            }
        }
        
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        if (!Validator.isValidEmail(email)) {
            views.MenuView.showError("Invalid email format! Must contain @ and .");
            return null;
        }
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        
        if (!Validator.isValidPhone(phone)) {
            views.MenuView.showError("Invalid phone number! Must be 10-11 digits");
            return null;
        }
        
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        
        // Create new customer
        Customer newCustomer = new Customer(id, name, email, password, phone, address);
        users.add(newCustomer);
        
        views.MenuView.showSuccess("Registration successful! Customer " + name + " added.");
        return newCustomer;
    }
}