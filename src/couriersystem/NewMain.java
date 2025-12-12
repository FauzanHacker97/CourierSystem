package couriersystem;

import models.*;
import services.*;
import utils.Validator;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Scanner;

public class NewMain {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Parcel> parcels = new ArrayList<>();
    private static ArrayList<Staff> staffList = new ArrayList<>();
    private static ParcelService parcelService = new ParcelService();
    private static PaymentService paymentService = new PaymentService();
    
    private static Customer currentCustomer = null;
    private static Staff currentStaff = null;
    private static boolean isAdmin = false;
    
    public static void main(String[] args) {
        initializeSampleData();
        loginMenu();
    }
    
    private static void loginMenu() {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n=== COURIER SYSTEM LOGIN ===");
            System.out.println("1. Customer Login");
            System.out.println("2. Staff Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Register as New Customer");
            System.out.println("5. Exit System");
            System.out.print("Select: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    customerLogin();
                    break;
                case 2:
                    staffLogin();
                    break;
                case 3:
                    adminLogin();
                    break;
                case 4:
                    registerCustomer();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
     private static void customerLogin() {
        System.out.print("\nEnter Customer ID: ");
        String id = scanner.nextLine();
        
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                currentCustomer = customer;
                System.out.println("Welcome, " + customer.getName() + "!");
                customerDashboard();
                return;
            }
        }
        System.out.println("Customer not found!");
    }
    
    private static void initializeSampleData() {
        // Create sample customers
        customers.add(new Customer("C001", "Ali", "012-3456789", "ali@email.com"));
        customers.add(new Customer("C002", "Siti", "013-4567890", "siti@email.com"));
        
        // Create sample staff
        staffList.add(new Staff("S001", "Ahmad", "Delivery", "011-2223333"));
        staffList.add(new Staff("S002", "Muthu", "Warehouse", "011-4445555"));
        
        // Create sample parcels
        // NEW CODE (Correct):
        parcels.add(new Parcel("P001", "Small Box", 2.5, "Pending", 
        customers.get(0), 
        "123 Main St, KL", "John Doe", "012-3456789"));
    }
    
    private static void customerDashboard() {
    boolean logout = false;
    
    while (!logout && currentCustomer != null) {
        System.out.println("\n=== CUSTOMER DASHBOARD ===");
        System.out.println("Logged in as: " + currentCustomer.getName());
        System.out.println("1. Send New Parcel");
        System.out.println("2. View My Parcels");
        System.out.println("3. Track Parcel");
        System.out.println("4. Make Payment");
        System.out.println("5. View My Profile");
        System.out.println("6. Logout");
        System.out.print("Select: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                createParcelForCurrentCustomer();
                break;
            case 2:
                viewCustomerParcels();
                break;
            case 3:
                trackCustomerParcel();
                break;
            case 4:
                makePaymentForCustomer();
                break;
            case 5:
                viewCustomerProfile();
                break;
            case 6:
                logout = true;
                currentCustomer = null;
                System.out.println("Logged out successfully!");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}
    
   // Add these methods after customerLogin() method

private static void staffLogin() {
    System.out.print("\nEnter Staff ID: ");
    String id = scanner.nextLine();
    
    System.out.print("Enter Password (default: 'staff123'): ");
    String password = scanner.nextLine();
    
    for (Staff staff : staffList) {
        if (staff.getId().equals(id)) {
            // Simple password check (for demo)
            if (password.equals("staff123")) {
                currentStaff = staff;
                System.out.println("Welcome, Staff " + staff.getName() + "!");
                staffDashboard();
                return;
            } else {
                System.out.println("Incorrect password!");
                return;
            }
        }
    }
    System.out.println("Staff not found!");
}

private static void adminLogin() {
    System.out.print("\nEnter Admin Username: ");
    String username = scanner.nextLine();
    
    System.out.print("Enter Admin Password: ");
    String password = scanner.nextLine();
    
    // Simple admin credentials (for demo)
    if (username.equals("admin") && password.equals("admin123")) {
        isAdmin = true;
        System.out.println("Welcome, Administrator!");
        adminDashboard();
    } else {
        System.out.println("Invalid admin credentials!");
    }
}

private static void staffDashboard() {
    boolean logout = false;
    
    while (!logout && currentStaff != null) {
        System.out.println("\n=== STAFF DASHBOARD ===");
        System.out.println("Staff: " + currentStaff.getName());
        System.out.println("Department: " + currentStaff.getDepartment());
        System.out.println("1. View My Details");
        System.out.println("2. View Assigned Parcels");
        System.out.println("3. Update Parcel Status");
        System.out.println("4. View All Staff");
        System.out.println("5. Mark Parcel as Delivered");
        System.out.println("6. Logout");
        System.out.print("Select: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                viewStaffDetails();
                break;
            case 2:
                viewStaffParcels();
                break;
            case 3:
                updateParcelStatus();
                break;
            case 4:
                displayAllStaff();
                break;
            case 5:
                markParcelDelivered();
                break;
            case 6:
                logout = true;
                currentStaff = null;
                System.out.println("Staff logged out successfully!");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}

private static void adminDashboard() {
    boolean logout = false;
    
    while (!logout && isAdmin) {
        System.out.println("\n=== ADMIN DASHBOARD ===");
        System.out.println("1. View All Customers");
        System.out.println("2. View All Staff");
        System.out.println("3. View All Parcels");
        System.out.println("4. View System Statistics");
        System.out.println("5. Search Parcel by ID");
        System.out.println("6. Assign Staff to Parcel");
        System.out.println("7. Generate Report");
        System.out.println("8. Logout");
        System.out.print("Select: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                displayAllCustomers();
                break;
            case 2:
                displayAllStaff();
                break;
            case 3:
                displayAllParcels();
                break;
            case 4:
                displayStatistics();
                break;
            case 5:
                searchParcel();
                break;
            case 6:
                assignStaffToParcel();
                break;
            case 7:
                generateReport();
                break;
            case 8:
                logout = true;
                isAdmin = false;
                System.out.println("Admin logged out successfully!");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}
    
    // In Main.java (continued)

private static void registerCustomer() {
    System.out.println("\n--- REGISTER NEW CUSTOMER ---");
    
    System.out.print("Enter Customer ID: ");
    String id = scanner.nextLine();
    
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    
    System.out.print("Enter Phone: ");
    String phone = scanner.nextLine();
    
    System.out.print("Enter Email: ");
    String email = scanner.nextLine();
    
    // Validate using Validator class
    if (!Validator.isValidEmail(email)) {
        System.out.println("Invalid email format!");
        return;
    }
    
    if (!Validator.isValidPhone(phone)) {
        System.out.println("Invalid phone number!");
        return;
    }
    
    Customer newCustomer = new Customer(id, name, phone, email);
    customers.add(newCustomer);
    System.out.println("Customer registered successfully!");
}

private static void createParcelForCurrentCustomer() {
    if (currentCustomer == null) {
        System.out.println("Please login first!");
        return;
    }
    
    System.out.println("\n--- SEND NEW PARCEL ---");
    System.out.println("Sender: " + currentCustomer.getName());
    
    System.out.print("Enter Parcel Description: ");
    String description = scanner.nextLine();
    
    System.out.print("Enter Weight (kg): ");
    double weight = scanner.nextDouble();
    scanner.nextLine(); // Clear buffer
    
    if (!Validator.isValidWeight(weight)) {
        System.out.println("Invalid weight! Must be between 0.1-100 kg");
        return;
    }
    
    // Auto-generate parcel ID
    String parcelId = "P" + (parcels.size() + 1001);
    
    System.out.print("Enter Destination Address: ");
    String destination = scanner.nextLine();
    
    System.out.print("Enter Recipient Name: ");
    String recipient = scanner.nextLine();
    
    System.out.print("Enter Recipient Phone: ");
    String recipientPhone = scanner.nextLine();
    
    // Create parcel with current customer as sender
    Parcel newParcel = new Parcel(parcelId, description, weight, 
                                  "Pending", currentCustomer, 
                                  destination, recipient, recipientPhone);
    
    parcels.add(newParcel);
    parcelService.addParcel(newParcel);
    
   // In createParcelForCurrentCustomer() method:
System.out.println("Parcel created successfully!");
double estimatedCost = calculateShippingCost(weight);
System.out.println("Your Parcel ID: " + parcelId);
System.out.println("Estimated Shipping Cost: RM" + estimatedCost);
System.out.println("Please make payment to proceed with shipping.");
}

private static void viewCustomerParcels() {
    if (currentCustomer == null) {
        System.out.println("Please login first!");
        return;
    }
    
    System.out.println("\n=== YOUR PARCELS ===");
    
    boolean hasParcels = false;
    for (Parcel parcel : parcels) {
        // Check if this parcel belongs to current customer
        if (parcel.getSender().getId().equals(currentCustomer.getId())) {
            System.out.println("Parcel ID: " + parcel.getParcelId());
            System.out.println("Description: " + parcel.getDescription());
            System.out.println("Status: " + parcel.getStatus());
            System.out.println("Destination: " + parcel.getDestination());
            System.out.println("Recipient: " + parcel.getRecipientName());
            
            if (parcel.getAssignedStaff() != null) {
                System.out.println("Assigned Staff: " + parcel.getAssignedStaff().getName());
            }
            System.out.println("-------------------");
            hasParcels = true;
        }
    }
    
    if (!hasParcels) {
        System.out.println("You have no parcels yet.");
    }
}

private static void trackCustomerParcel() {
    if (currentCustomer == null) {
        System.out.println("Please login first!");
        return;
    }
    
    // First show customer's parcels
    System.out.println("\n=== YOUR PARCELS ===");
    ArrayList<Parcel> customerParcels = new ArrayList<>();
    
    int count = 0;
    for (Parcel parcel : parcels) {
        if (parcel.getSender().getId().equals(currentCustomer.getId())) {
            System.out.println((count + 1) + ". " + parcel.getParcelId() + 
                             " - " + parcel.getDescription() + 
                             " (" + parcel.getStatus() + ")");
            customerParcels.add(parcel);
            count++;
        }
    }
    
    if (count == 0) {
        System.out.println("You have no parcels to track.");
        return;
    }
    
    System.out.print("\nSelect parcel number to track (1-" + count + "): ");
    
    try {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear the newline
        
        if (choice < 1 || choice > customerParcels.size()) {
            System.out.println("Invalid selection! Please enter a number between 1 and " + count);
            return;
        }
        
        Parcel selectedParcel = customerParcels.get(choice - 1);
        selectedParcel.displayDetails();
        selectedParcel.displayTrackingHistory();
        
    } catch (InputMismatchException e) {
        System.out.println("Invalid input! Please enter a NUMBER (1, 2, 3, etc).");
        scanner.nextLine(); // Clear invalid input
    }
}

private static void makePaymentForCustomer() {
    if (currentCustomer == null) {
        System.out.println("Please login first!");
        return;
    }
    
    // Show parcels that need payment (status = "Pending")
    System.out.println("\n=== PARCELS REQUIRING PAYMENT ===");
    ArrayList<Parcel> unpaidParcels = new ArrayList<>();
    
    int count = 0;
    for (Parcel parcel : parcels) {
        if (parcel.getSender().getId().equals(currentCustomer.getId()) &&
            parcel.getStatus().equals("Pending")) {  // Changed from "Pending Payment"
            
            double amount = calculateShippingCost(parcel.getWeight());
            
            System.out.println((count + 1) + ". " + parcel.getParcelId());
            System.out.println("   Description: " + parcel.getDescription());
            System.out.println("   Weight: " + parcel.getWeight() + " kg");
            System.out.println("   Shipping Cost: RM" + amount);
            System.out.println("   Destination: " + parcel.getDestination());
            System.out.println("   -----------------");
            unpaidParcels.add(parcel);
            count++;
        }
    }
    
    if (count == 0) {
        System.out.println("No payments pending. All your parcels are either paid or delivered.");
        return;
    }
    
    System.out.print("\nSelect parcel to pay for (1-" + count + "): ");
    
    try {
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice < 1 || choice > unpaidParcels.size()) {
            System.out.println("Invalid selection!");
            return;
        }
        
        Parcel selectedParcel = unpaidParcels.get(choice - 1);
        double amount = calculateShippingCost(selectedParcel.getWeight());
        
        System.out.println("\n=== PAYMENT DETAILS ===");
        System.out.println("Parcel ID: " + selectedParcel.getParcelId());
        System.out.println("Description: " + selectedParcel.getDescription());
        System.out.println("Weight: " + selectedParcel.getWeight() + " kg");
        System.out.println("Destination: " + selectedParcel.getDestination());
        System.out.println("Recipient: " + selectedParcel.getRecipientName());
        System.out.println("Total Amount: RM" + amount);
        System.out.println("----------------------");
        
        System.out.println("Select Payment Method:");
        System.out.println("1. Credit/Debit Card");
        System.out.println("2. Online Banking");
        System.out.println("3. Cash on Delivery");
        System.out.print("Choice: ");
        
        int methodChoice = scanner.nextInt();
        scanner.nextLine();
        
        String paymentMethod = "";
        switch (methodChoice) {
            case 1:
                paymentMethod = "Credit Card";
                System.out.print("Enter card number: ");
                String card = scanner.nextLine();
                System.out.print("Enter expiry (MM/YY): ");
                String expiry = scanner.nextLine();
                System.out.println("Processing card payment...");
                break;
            case 2:
                paymentMethod = "Online Banking";
                System.out.println("Redirecting to bank portal...");
                break;
            case 3:
                paymentMethod = "Cash on Delivery";
                System.out.println("Payment will be collected upon delivery.");
                break;
            default:
                paymentMethod = "Unknown";
        }
        
        // Create and process payment
        Payment payment = paymentService.createPayment(selectedParcel, amount, paymentMethod);
        payment.processPayment();
        
        // Update parcel status
        selectedParcel.setStatus("Paid - Processing"); // Changed status
        
        // Update customer's total spent
        currentCustomer.addToTotalSpent(amount);
        
        System.out.println("✅ PAYMENT SUCCESSFUL!");
        System.out.println("Payment ID: " + payment.getPaymentId());
        System.out.println("Parcel status updated to: " + selectedParcel.getStatus());
        System.out.println("You will be notified when parcel is shipped.");
        
    } catch (InputMismatchException e) {
        System.out.println("Invalid input! Please enter numbers only.");
        scanner.nextLine();
    }
}

private static double calculateShippingCost(double weight) {
    // Simple shipping calculation
    double baseRate = 5.0; // RM5 base rate
    double perKgRate = 3.0; // RM3 per kg
    
    return baseRate + (weight * perKgRate);
}

private static void viewStaffParcels() {
    System.out.print("\nEnter Staff ID: ");
    String staffId = scanner.nextLine();
    
    Staff staff = null;
    for (Staff s : staffList) {
        if (s.getId().equals(staffId)) {
            staff = s;
            break;
        }
    }
    
    if (staff == null) {
        System.out.println("Staff not found!");
        return;
    }
    
    System.out.println("\nParcels assigned to " + staff.getName() + ":");
    int count = 0;
    
    // Control structure: for loop with array
    for (Parcel parcel : parcels) {
        if (parcel.getAssignedStaff() != null && 
            parcel.getAssignedStaff().getId().equals(staffId)) {
            System.out.println(parcel.getParcelId() + " - " + 
                             parcel.getDescription() + " (" + 
                             parcel.getStatus() + ")");
            count++;
        }
    }
    
    if (count == 0) {
        System.out.println("No parcels assigned yet.");
    }
}

private static void updateParcelStatus() {
    System.out.print("\nEnter Parcel ID: ");
    String parcelId = scanner.nextLine();
    
    System.out.println("Select new status:");
    System.out.println("1. Processing");
    System.out.println("2. In Transit");
    System.out.println("3. Out for Delivery");
    System.out.println("4. Delivered");
    System.out.println("5. Returned");
    System.out.print("Select (1-5): ");
    
    try {
        int statusChoice = scanner.nextInt();
        scanner.nextLine();
        
        String[] statuses = {"Processing", "In Transit", 
                             "Out for Delivery", "Delivered", "Returned"};
        
        if (statusChoice < 1 || statusChoice > statuses.length) {
            System.out.println("Invalid choice! Please enter 1-5.");
            return;
        }
        
        String newStatus = statuses[statusChoice - 1];
        boolean updated = parcelService.updateParcelStatus(parcelId, newStatus);
        
        if (updated) {
            System.out.println("Status updated to: " + newStatus);
        } else {
            System.out.println("Parcel not found!");
        }
        
    } catch (InputMismatchException e) {
        System.out.println("Invalid input! Please enter a number 1-5.");
        scanner.nextLine();
    }
}

private static void displayAllParcels() {
    System.out.println("\n=== ALL PARCELS IN SYSTEM ===");
    
    if (parcels.isEmpty()) {
        System.out.println("No parcels in system.");
        return;
    }
    
    // Using for-each loop
    for (Parcel parcel : parcels) {
        parcel.displayDetails();
        System.out.println("-------------------");
    }
}

private static void displayAllCustomers() {
    System.out.println("\n=== ALL CUSTOMERS ===");
    for (Customer customer : customers) {
        customer.displayInfo();
        System.out.println("-------------------");
    }
}

private static void displayAllStaff() {
    System.out.println("\n=== ALL STAFF ===");
    for (Staff staff : staffList) {
        staff.displayInfo();
        System.out.println("-------------------");
    }
}

private static void displayStatistics() {
    System.out.println("\n=== SYSTEM STATISTICS ===");
    System.out.println("Total Customers: " + customers.size());
    System.out.println("Total Staff: " + staffList.size());
    System.out.println("Total Parcels: " + parcels.size());
    
    // Count parcels by status (with flexible matching)
    int pendingCount = 0;
    int processingCount = 0;
    int inTransitCount = 0;
    int deliveredCount = 0;
    
    for (Parcel parcel : parcels) {
        String status = parcel.getStatus().toLowerCase();
        
        if (status.contains("pending")) {
            pendingCount++;
        } else if (status.contains("processing")) {
            processingCount++;
        } else if (status.contains("transit")) {
            inTransitCount++;
        } else if (status.contains("delivered")) {
            deliveredCount++;
        }
    }
    
    System.out.println("Pending Parcels: " + pendingCount);
    System.out.println("Processing Parcels: " + processingCount);
    System.out.println("In Transit Parcels: " + inTransitCount);
    System.out.println("Delivered Parcels: " + deliveredCount);
    
    System.out.println("Total Revenue: RM" + paymentService.calculateTotalRevenue());
}

private static void searchParcel() {
    System.out.print("\nEnter Parcel ID to search: ");
    String parcelId = scanner.nextLine();
    
    // Using String methods for comparison
    boolean found = false;
    for (Parcel parcel : parcels) {
        if (parcel.getParcelId().equalsIgnoreCase(parcelId)) {
            parcel.displayDetails();
            found = true;
            break;
        }
    }
    
    if (!found) {
        System.out.println("Parcel not found!");
    }
}

private static void viewStaffDetails() {
    System.out.println("\n--- VIEW STAFF DETAILS ---");
    
    if (staffList.isEmpty()) {
        System.out.println("No staff registered yet!");
        return;
    }
    
    System.out.println("1. View All Staff");
    System.out.println("2. Search Staff by ID");
    System.out.print("Select option: ");
    
    int choice = scanner.nextInt();
    scanner.nextLine();
    
    switch (choice) {
        case 1:
            viewAllStaffDetails();
            break;
        case 2:
            searchStaffById();
            break;
        default:
            System.out.println("Invalid choice!");
    }
}

private static void viewAllStaffDetails() {
    System.out.println("\n=== ALL STAFF DETAILS ===");
    
    // Control structure: for loop with counter
    for (int i = 0; i < staffList.size(); i++) {
        Staff staff = staffList.get(i);
        System.out.println("\n--- Staff " + (i + 1) + " ---");
        System.out.println("Staff ID: " + staff.getId());
        System.out.println("Name: " + staff.getName());
        System.out.println("Department: " + staff.getDepartment());
        System.out.println("Phone: " + staff.getPhone());
        System.out.println("Parcels Delivered: " + staff.getParcelsDelivered());
        
        // Show assigned parcels if any
        showStaffAssignedParcels(staff.getId());
    }
}

private static void searchStaffById() {
    System.out.print("\nEnter Staff ID to search: ");
    String staffId = scanner.nextLine();
    
    boolean found = false;
    
    // Control structure: enhanced for loop
    for (Staff staff : staffList) {
        // String method: equalsIgnoreCase for case-insensitive search
        if (staff.getId().equalsIgnoreCase(staffId)) {
            System.out.println("\n=== STAFF FOUND ===");
            System.out.println("ID: " + staff.getId());
            System.out.println("Name: " + staff.getName());
            System.out.println("Department: " + staff.getDepartment());
            System.out.println("Phone: " + Validator.formatPhoneNumber(staff.getPhone()));
            System.out.println("Parcels Delivered: " + staff.getParcelsDelivered());
            
            // Display performance rating
            displayStaffPerformance(staff);
            
            // Show assigned parcels
            showStaffAssignedParcels(staff.getId());
            
            found = true;
            break;
        }
    }
    
    if (!found) {
        System.out.println("Staff with ID '" + staffId + "' not found!");
    }
}

private static void showStaffAssignedParcels(String staffId) {
    System.out.println("\nCurrently Assigned Parcels:");
    
    int assignedCount = 0;
    // Array/Collection iteration
    for (Parcel parcel : parcels) {
        if (parcel.getAssignedStaff() != null && 
            parcel.getAssignedStaff().getId().equals(staffId)) {
            System.out.println("  - " + parcel.getParcelId() + ": " + 
                             parcel.getDescription() + " (" + 
                             parcel.getStatus() + ")");
            assignedCount++;
        }
    }
    
    if (assignedCount == 0) {
        System.out.println("  No parcels currently assigned.");
    } else {
        System.out.println("  Total assigned: " + assignedCount + " parcel(s)");
    }
}

private static void displayStaffPerformance(Staff staff) {
    int delivered = staff.getParcelsDelivered();
    
    System.out.print("Performance: ");
    
    // Control structure: if-else chain
    if (delivered >= 50) {
        System.out.println("⭐⭐⭐⭐⭐ Excellent (50+ deliveries)");
    } else if (delivered >= 30) {
        System.out.println("⭐⭐⭐⭐ Very Good (30-49 deliveries)");
    } else if (delivered >= 15) {
        System.out.println("⭐⭐⭐ Good (15-29 deliveries)");
    } else if (delivered >= 5) {
        System.out.println("⭐⭐ Fair (5-14 deliveries)");
    } else {
        System.out.println("⭐ Needs more experience (0-4 deliveries)");
    }
}

// Add this method for admin to assign staff
private static void assignStaffToParcel() {
    System.out.println("\n=== ASSIGN STAFF TO PARCEL ===");
    
    // Show unassigned parcels
    System.out.println("Unassigned Parcels:");
    ArrayList<Parcel> unassigned = new ArrayList<>();
    int count = 0;
    
    for (Parcel parcel : parcels) {
        if (parcel.getAssignedStaff() == null) {
            System.out.println((count + 1) + ". " + parcel.getParcelId() + 
                             " - " + parcel.getDescription());
            unassigned.add(parcel);
            count++;
        }
    }
    
    if (count == 0) {
        System.out.println("No unassigned parcels.");
        return;
    }
    
    System.out.print("\nSelect parcel number (1-" + count + "): ");
    
    try {
        int parcelChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // Clear buffer
        
        if (parcelChoice < 0 || parcelChoice >= unassigned.size()) {
            System.out.println("Invalid selection!");
            return;
        }
        
        Parcel selectedParcel = unassigned.get(parcelChoice);
        
        // Show available staff
        System.out.println("\nAvailable Staff:");
        for (int i = 0; i < staffList.size(); i++) {
            System.out.println((i + 1) + ". " + staffList.get(i).getName() + 
                             " (" + staffList.get(i).getDepartment() + ")");
        }
        
        System.out.print("Select staff number: ");
        int staffChoice = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (staffChoice < 0 || staffChoice >= staffList.size()) {
            System.out.println("Invalid selection!");
            return;
        }
        
        Staff selectedStaff = staffList.get(staffChoice);
        
        // Assign staff
        selectedParcel.setAssignedStaff(selectedStaff);
        System.out.println("✅ Staff " + selectedStaff.getName() + 
                         " assigned to parcel " + selectedParcel.getParcelId());
        
    } catch (InputMismatchException e) {
        System.out.println("Invalid input! Please enter numbers only.");
        scanner.nextLine(); // Clear invalid input
    }
}

// Add this method for report generation
private static void generateReport() {
    System.out.println("\n=== SYSTEM REPORT ===");
    System.out.println("Generated on: " + java.time.LocalDate.now());
    System.out.println("=================================");
    
    displayStatistics();
    
    System.out.println("\n--- Top Performing Staff ---");
    // Sort staff by parcels delivered
    staffList.sort((s1, s2) -> s2.getParcelsDelivered() - s1.getParcelsDelivered());
    
    for (int i = 0; i < Math.min(staffList.size(), 3); i++) {
        Staff staff = staffList.get(i);
        System.out.println((i + 1) + ". " + staff.getName() + 
                         " - " + staff.getParcelsDelivered() + 
                         " deliveries (" + staff.getPerformanceRating() + ")");
    }
    
    System.out.println("\n--- Recent Parcels ---");
    int recentCount = Math.min(parcels.size(), 5);
    for (int i = 0; i < recentCount; i++) {
        Parcel p = parcels.get(i);
        System.out.println(p.getParcelId() + " - " + 
                         p.getDescription() + " - " + p.getStatus());
    }
}

private static void markParcelDelivered() {
    System.out.println("\n--- MARK PARCEL AS DELIVERED ---");
    
    if (currentStaff == null) {
        System.out.println("Staff login required!");
        return;
    }
    
    // Show parcels assigned to this staff
    System.out.println("Your assigned parcels:");
    ArrayList<Parcel> staffParcels = new ArrayList<>();
    int count = 0;
    
    for (Parcel parcel : parcels) {
        if (parcel.getAssignedStaff() != null && 
            parcel.getAssignedStaff().getId().equals(currentStaff.getId()) &&
            !parcel.getStatus().equals("Delivered")) {
            
            System.out.println((count + 1) + ". " + parcel.getParcelId() + 
                             " - " + parcel.getDescription() + 
                             " (" + parcel.getStatus() + ")");
            staffParcels.add(parcel);
            count++;
        }
    }
    
    if (count == 0) {
        System.out.println("No parcels to mark as delivered.");
        return;
    }
    
    System.out.print("\nSelect parcel to mark as delivered (1-" + count + "): ");
    
    try {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        
        if (choice < 1 || choice > staffParcels.size()) {
            System.out.println("Invalid selection!");
            return;
        }
        
        Parcel selectedParcel = staffParcels.get(choice - 1);
        
        // Update parcel status
        selectedParcel.setStatus("Delivered");
        
        // Increment staff's delivery count
        currentStaff.incrementParcelsDelivered();
        
        System.out.println("✅ Parcel " + selectedParcel.getParcelId() + 
                         " marked as DELIVERED!");
        System.out.println("📈 " + currentStaff.getName() + 
                         "'s delivery count: " + currentStaff.getParcelsDelivered());
        
    } catch (InputMismatchException e) {
        System.out.println("Invalid input! Please enter a number.");
        scanner.nextLine(); // Clear invalid input
    }
}

private static void viewCustomerProfile() {
    if (currentCustomer == null) {
        System.out.println("Please login first!");
        return;
    }
    
    System.out.println("\n=== YOUR PROFILE ===");
    System.out.println("Customer ID: " + currentCustomer.getId());
    System.out.println("Name: " + currentCustomer.getName());
    System.out.println("Phone: " + currentCustomer.getPhone());
    System.out.println("Email: " + currentCustomer.getEmail());
    System.out.println("Total Spent: RM" + currentCustomer.getTotalSpent());
    
    // Show parcel statistics
    int totalParcels = 0;
    int deliveredParcels = 0;
    
    for (Parcel parcel : parcels) {
        if (parcel.getSender().getId().equals(currentCustomer.getId())) {
            totalParcels++;
            if (parcel.getStatus().equals("Delivered")) {
                deliveredParcels++;
            }
        }
    }
    
    System.out.println("\n=== PARCEL STATISTICS ===");
    System.out.println("Total Parcels Sent: " + totalParcels);
    System.out.println("Delivered: " + deliveredParcels);
    System.out.println("In Progress: " + (totalParcels - deliveredParcels));
    
    if (totalParcels > 0) {
        double successRate = (deliveredParcels * 100.0) / totalParcels;
        System.out.printf("Delivery Success Rate: %.1f%%\n", successRate);
    }
}

}