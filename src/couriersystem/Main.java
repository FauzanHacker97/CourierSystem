package couriersystem;

import controllers.*;
import models.*;
import services.*;
import utils.*;
import java.util.*;

public class Main {
    // Data collections - USING COMPOSITION
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Parcel> parcels = new ArrayList<>(); // SINGLE source for parcels
    private static ArrayList<Delivery> deliveries = new ArrayList<>();
    private static ArrayList<Vehicle> vehicles = new ArrayList<>();
    private static ArrayList<Payment> payments = new ArrayList<>();
    
    // Services - USING AGGREGATION
    private static ParcelService parcelService;
    private static PaymentService paymentService = new PaymentService();
    
    // Controllers
    private static AuthController authController;
    private static CustomerController customerController;
    private static StaffController staffController;
    private static AdminController adminController;
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("========================================\n");
        System.out.println("    COURIER PARCEL MANAGEMENT SYSTEM      ");
        System.out.println("          Phase 2 - OOP Project           ");
        
        initializeData();
        initializeControllers();
        startSystem();
    }
    
  private static void initializeData() {
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
    
    users.add(c1);
    users.add(c2);
    users.add(s1);
    users.add(s2);
    
    // ✅ Use sequential IDs starting from P001
    Parcel p1 = ParcelFactory.createParcel("STANDARD", "P001", c1, c2, 
                                          2.5, "30x20x15", "Documents", "");
    Parcel p2 = ParcelFactory.createParcel("EXPRESS", "P002", c2, c1, 
                                          1.2, "25x15x10", "Urgent Gift", "");
    
    parcels.add(p1);
    parcels.add(p2);
    
    // Create vehicles
    vehicles.add(new Vehicle("V001", "Van", "ABC1234", 500));
    vehicles.add(new Vehicle("V002", "Motorcycle", "DEF5678", 50));
    
    // Create deliveries
    deliveries.add(new Delivery("D001", p1, s1));  // First delivery
    deliveries.add(new Delivery("D002", p2, s2));  // Second delivery
    
    // Assign vehicle silently
    Vehicle van = vehicles.get(0);
    van.assignVehicle("D001");
    deliveries.get(0).setAssignedVehicle(van);
}
    
    private static void initializeControllers() {
        // Initialize services with the single parcels list
        parcelService = new ParcelService(parcels);
        
        // Initialize controllers with their dependencies
        authController = new AuthController(scanner, users);
        customerController = new CustomerController(scanner, parcelService, 
                                                   paymentService, parcels, 
                                                   deliveries, payments, users, vehicles);
        staffController = new StaffController(scanner, deliveries, parcels, 
                                             vehicles, users);
        adminController = new AdminController(scanner, users, parcels, deliveries, 
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
        
        // 1. Demonstrate arrays from utils package
        ArrayDemo.demonstrateArrays();
        
        // 2. Demonstrate arrays from services
        parcelService.demonstrateArrayUsage();
        
        // 3. Show conversion from ArrayList to Array
        System.out.println("\n=== ARRAYLIST TO ARRAY CONVERSION ===");
        
        // Convert parcels ArrayList to Array
        Parcel[] parcelArray = parcels.toArray(new Parcel[0]);
        System.out.println("ArrayList<parcels>.size() = " + parcels.size());
        System.out.println("parcelArray.length = " + parcelArray.length);
        
        if (parcelArray.length > 0) {
            System.out.println("First parcel in array: " + parcelArray[0].getParcelId());
            System.out.println("Last parcel in array: " + parcelArray[parcelArray.length - 1].getParcelId());
        }
        
        // 4. Demonstrate primitive array operations
        System.out.println("\n=== PRIMITIVE ARRAY OPERATIONS ===");
        int[] deliveryCounts = new int[7]; // Days of week
        deliveryCounts[0] = 5;  // Monday
        deliveryCounts[1] = 7;  // Tuesday
        deliveryCounts[2] = 6;  // Wednesday
        System.out.println("int[] deliveryCounts:");
        for (int i = 0; i < deliveryCounts.length; i++) {
            System.out.println("  Day " + (i+1) + ": " + deliveryCounts[i] + " deliveries");
        }
        
        System.out.println("\n✅ All array requirements demonstrated:");
        System.out.println("   ✓ Primitive arrays (int[], double[])");
        System.out.println("   ✓ String arrays (String[])");
        System.out.println("   ✓ Object arrays (Parcel[], Vehicle[])");
        System.out.println("   ✓ ArrayList (dynamic arrays)");
        System.out.println("   ✓ Array operations (access, iteration, length, conversion)");
        System.out.println("=".repeat(70));
        
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}