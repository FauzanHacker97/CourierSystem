package couriersystem;

import controllers.*;
import models.*;
import services.*;
import utils.*;
import java.util.*;

public class Main {
    // Data collections - USING COMPOSITION
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Parcel> parcels = new ArrayList<>();
    private static ArrayList<Delivery> deliveries = new ArrayList<>();
    private static ArrayList<Vehicle> vehicles = new ArrayList<>();
    private static ArrayList<Payment> payments = new ArrayList<>();
    
    // Services - USING AGGREGATION
    private static ParcelService parcelService = new ParcelService();
    private static PaymentService paymentService = new PaymentService();
    private static ArrayDemo arrayDemo = new ArrayDemo();
    
    // Controllers
    private static AuthController authController;
    private static CustomerController customerController;
    private static StaffController staffController;
    private static AdminController adminController;
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== COURIER PARCEL MANAGEMENT SYSTEM ===");
        System.out.println("           Phase 2 - OOP Project");
        System.out.println("========================================\n");
        
        initializeData();
        initializeControllers();
        startSystem();
    }
    
    private static void initializeData() {
        System.out.println("Initializing sample data...\n");
        
        // Create sample customers
        Customer c1 = new Customer("C001", "Ali", "ali@email.com", "pass123", 
                                  "012-3456789", "123 Main St, KL");
        Customer c2 = new Customer("C002", "Siti", "siti@email.com", "pass456", 
                                  "013-4567890", "456 Oak Ave, PJ");
        
        // Create sample staff
        Staff s1 = new Staff("S001", "Raju", "raju@courier.com", "staff123", 
                            "011-2223333", "Delivery Driver", 2500);
        Staff s2 = new Staff("S002", "Mei Ling", "mei@courier.com", "staff456", 
                            "011-4445555", "Warehouse Manager", 3200);
        
        // Demonstrate INHERITANCE: Customer and Staff extend User
        users.add(c1);  // Customer IS-A User
        users.add(c2);  // Customer IS-A User
        users.add(s1);  // Staff IS-A User
        users.add(s2);  // Staff IS-A User
        
        // Create sample parcels using FACTORY PATTERN
        Parcel p1 = ParcelFactory.createParcel("STANDARD", "P001", c1, c2, 
                                              2.5, "30x20x15", "Documents", "");
        Parcel p2 = ParcelFactory.createParcel("EXPRESS", "P002", c2, c1, 
                                              1.2, "25x15x10", "Urgent Gift", "");
        
        parcels.add(p1);
        parcels.add(p2);
        
        // Demonstrate ARRAYS: Add to array-based services
        parcelService.addParcel(p1);  // Adds to Parcel[] parcelArray
        parcelService.addParcel(p2);  // Adds to Parcel[] parcelArray
        arrayDemo.addToArchive(p1);   // Adds to Parcel[] parcelArchive
        arrayDemo.addToArchive(p2);   // Adds to Parcel[] parcelArchive
        
        // Create vehicles
        vehicles.add(new Vehicle("V001", "Van", "ABC1234", 500));
        vehicles.add(new Vehicle("V002", "Motorcycle", "DEF5678", 50));
        
        // Create deliveries - DEMONSTRATING COMPOSITION
        // Delivery HAS-A Parcel and HAS-A Staff
        deliveries.add(new Delivery("D001", p1, s1));
        deliveries.add(new Delivery("D002", p2, s2));
        
        // Assign vehicle to delivery - DEMONSTRATING AGGREGATION
        // Delivery associates with Vehicle
        vehicles.get(0).assignVehicle("D001");
        deliveries.get(0).setAssignedVehicle(vehicles.get(0));
        
        System.out.println("✅ Sample data initialized:");
        System.out.println("   - " + users.size() + " users (Inheritance demonstrated)");
        System.out.println("   - " + parcels.size() + " parcels (Factory Pattern used)");
        System.out.println("   - " + vehicles.size() + " vehicles");
        System.out.println("   - " + deliveries.size() + " deliveries (Composition/Aggregation)");
        System.out.println("   - Arrays: parcelService has " + parcelService.getParcelCount() + " parcels");
        System.out.println();
    }
    
    private static void initializeControllers() {
        // Initialize controllers with their dependencies
        authController = new AuthController(scanner, users);
        customerController = new CustomerController(scanner, parcelService, 
                                                   paymentService, parcels, 
                                                   deliveries, payments, users);
        staffController = new StaffController(scanner, deliveries, parcels, 
                                             vehicles, users);
        adminController = new AdminController(scanner, parcelService, paymentService,
                                             arrayDemo, users, parcels, deliveries, 
                                             vehicles, payments);
    }
    
    private static void startSystem() {
        boolean exit = false;
        
        while (!exit) {
            views.MenuView.showMainMenu();
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1: handleLogin(); break;
                    case 2: handleRegistration(); break;
                    case 3: demonstrateArraysForRubric(); break;
                    case 4: 
                        exit = true; 
                        System.out.println("\nThank you for using Courier System!");
                        break;
                    default: 
                        System.out.println("Invalid option! Choose 1-4");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number (1-4)!");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
    
    private static void handleLogin() {
        User user = authController.login();
        
        if (user != null) {
            // POLYMORPHISM: Same User type, different behaviors
            if (user instanceof Customer) {
                customerController.showMenu((Customer) user);
            } else if (user instanceof Staff) {
                Staff staff = (Staff) user;
                if (staff.getUserId().equals("ADMIN")) {
                    adminController.showMenu();
                } else {
                    staffController.showMenu(staff);
                }
            }
        }
    }
    
    private static void handleRegistration() {
        Customer newCustomer = authController.registerCustomer();
        if (newCustomer != null) {
            System.out.println("Please login with your new account.");
        }
    }
    
    private static void demonstrateArraysForRubric() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("  📊 ARRAY DEMONSTRATION - PHASE 2 RUBRIC REQUIREMENTS");
        System.out.println("=".repeat(70));
        
        // Demonstrate arrays from utils package
        ArrayDemo.demonstrateArrays();
        
        // Demonstrate arrays from services
        parcelService.demonstrateArrayUsage();
        
        // Show array from main system
        System.out.println("\n=== SYSTEM DATA IN ARRAYLISTS ===");
        System.out.println("users ArrayList: " + users.size() + " users");
        System.out.println("parcels ArrayList: " + parcels.size() + " parcels");
        System.out.println("vehicles ArrayList: " + vehicles.size() + " vehicles");
        
        System.out.println("\n✅ All array requirements demonstrated:");
        System.out.println("   ✓ Primitive arrays (int[], double[])");
        System.out.println("   ✓ String arrays");
        System.out.println("   ✓ Object arrays (Parcel[], Vehicle[])");
        System.out.println("   ✓ ArrayList (dynamic arrays)");
        System.out.println("=".repeat(70));
    }
}