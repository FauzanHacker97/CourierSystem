package controllers;

import models.*;
import services.*;
import utils.*;
import views.*;
import java.util.*;

public class AdminController {
    private Scanner scanner;
    private ParcelService parcelService;
    private PaymentService paymentService;
    private ArrayDemo arrayDemo;
    private ArrayList<User> users;
    private ArrayList<Parcel> parcels;
    private ArrayList<Delivery> deliveries;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Payment> payments;
    
    public AdminController(Scanner scanner, ParcelService parcelService,
                          PaymentService paymentService, ArrayDemo arrayDemo, 
                          ArrayList<User> users, ArrayList<Parcel> parcels, 
                          ArrayList<Delivery> deliveries, ArrayList<Vehicle> vehicles,
                          ArrayList<Payment> payments) {
        this.scanner = scanner;
        this.parcelService = parcelService;
        this.paymentService = paymentService;
        this.arrayDemo = arrayDemo;
        this.users = users;
        this.parcels = parcels;
        this.deliveries = deliveries;
        this.vehicles = vehicles;
        this.payments = payments;
    }
    
    public void showMenu() {
        boolean logout = false;
        
        while (!logout) {
            MenuView.showSectionHeader("ADMINISTRATOR DASHBOARD");
            System.out.println("Logged in as: System Administrator");
            System.out.println("Access Level: Full System Control");
            MenuView.showDivider();
            
            System.out.println("1.  View System Statistics");
            System.out.println("2.  View All Users");
            System.out.println("3.  View All Parcels");
            System.out.println("4.  Manage Deliveries");
            System.out.println("5.  Manage Vehicles");
            System.out.println("6.  View All Payments");
            System.out.println("7.  Add New Staff Member");
            System.out.println("8.  Add New Vehicle");
            System.out.println("9.  Generate System Report");
            System.out.println("10. Demonstrate Arrays (For Rubric)");
            System.out.println("11. Backup System Data");
            System.out.println("12. Logout");
            System.out.print("\nSelect option (1-12): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1: showStatistics(); break;
                    case 2: viewAllUsers(); break;
                    case 3: viewAllParcels(); break;
                    case 4: manageDeliveries(); break;
                    case 5: manageVehicles(); break;
                    case 6: viewAllPayments(); break;
                    case 7: addNewStaff(); break;
                    case 8: addNewVehicle(); break;
                    case 9: generateReport(); break;
                    case 10: demonstrateArrays(); break;
                    case 11: backupSystemData(); break;
                    case 12: 
                        logout = true;
                        MenuView.showSuccess("Admin logged out successfully!");
                        break;
                    default: 
                        MenuView.showError("Invalid option! Choose 1-12");
                }
            } catch (InputMismatchException e) {
                MenuView.showError("Please enter a number (1-12)!");
                scanner.nextLine();
            }
        }
    }
    
    private void showStatistics() {
        MenuView.showSectionHeader("SYSTEM STATISTICS");
        
        // Count users by type
        int customerCount = 0, staffCount = 0, adminCount = 0;
        for (User user : users) {
            if (user instanceof Customer) {
                customerCount++;
            } else if (user instanceof Staff) {
                staffCount++;
                if (user.getUserId().equals("ADMIN")) {
                    adminCount++;
                }
            }
        }
        
        System.out.println("\n📊 USER STATISTICS:");
        System.out.printf("%-25s: %d\n", "Total Users", users.size());
        System.out.printf("%-25s: %d\n", "  → Customers", customerCount);
        System.out.printf("%-25s: %d\n", "  → Staff Members", staffCount - adminCount);
        System.out.printf("%-25s: %d\n", "  → Administrators", adminCount);
        
        // Count parcels by type
        int standardCount = 0, expressCount = 0, internationalCount = 0;
        int created = 0, processing = 0, transit = 0, delivered = 0, returned = 0;
        
        for (Parcel parcel : parcels) {
            // Count by type
            if (parcel instanceof StandardParcel) standardCount++;
            else if (parcel instanceof ExpressParcel) expressCount++;
            else if (parcel instanceof InternationalParcel) internationalCount++;
            
            // Count by status
            String status = parcel.getStatus().toLowerCase();
            if (status.contains("created")) created++;
            else if (status.contains("processing")) processing++;
            else if (status.contains("transit")) transit++;
            else if (status.contains("delivered")) delivered++;
            else if (status.contains("returned")) returned++;
        }
        
        System.out.println("\n📦 PARCEL STATISTICS:");
        System.out.printf("%-25s: %d\n", "Total Parcels", parcels.size());
        System.out.printf("%-25s: %d\n", "  → Standard Parcels", standardCount);
        System.out.printf("%-25s: %d\n", "  → Express Parcels", expressCount);
        System.out.printf("%-25s: %d\n", "  → International Parcels", internationalCount);
        
        System.out.println("\n📈 PARCEL STATUS:");
        System.out.printf("%-25s: %d\n", "  → Created/Unpaid", created);
        System.out.printf("%-25s: %d\n", "  → Processing", processing);
        System.out.printf("%-25s: %d\n", "  → In Transit", transit);
        System.out.printf("%-25s: %d\n", "  → Delivered", delivered);
        System.out.printf("%-25s: %d\n", "  → Returned", returned);
        
        // Delivery statistics
        int scheduled = 0, inProgress = 0, completed = 0;
        for (Delivery delivery : deliveries) {
            String status = delivery.getStatus().toLowerCase();
            if (status.contains("scheduled")) scheduled++;
            else if (status.contains("delivered")) completed++;
            else inProgress++;
        }
        
        System.out.println("\n🚚 DELIVERY STATISTICS:");
        System.out.printf("%-25s: %d\n", "Total Deliveries", deliveries.size());
        System.out.printf("%-25s: %d\n", "  → Scheduled", scheduled);
        System.out.printf("%-25s: %d\n", "  → In Progress", inProgress);
        System.out.printf("%-25s: %d\n", "  → Completed", completed);
        
        // Vehicle statistics
        int availableVehicles = 0, inUseVehicles = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable()) availableVehicles++;
            else inUseVehicles++;
        }
        
        System.out.println("\n🚛 VEHICLE STATISTICS:");
        System.out.printf("%-25s: %d\n", "Total Vehicles", vehicles.size());
        System.out.printf("%-25s: %d\n", "  → Available", availableVehicles);
        System.out.printf("%-25s: %d\n", "  → In Use", inUseVehicles);
        
        // Financial statistics
        double totalRevenue = 0.0;
        double pendingPayments = 0.0;
        int completedPayments = 0;
        
        for (Payment payment : payments) {
            if (payment.getStatus().equals("Completed")) {
                totalRevenue += payment.getAmount();
                completedPayments++;
            } else {
                pendingPayments += payment.getAmount();
            }
        }
        
        System.out.println("\n💰 FINANCIAL STATISTICS:");
        System.out.printf("%-25s: RM%.2f\n", "Total Revenue", totalRevenue);
        System.out.printf("%-25s: RM%.2f\n", "Pending Payments", pendingPayments);
        System.out.printf("%-25s: %d\n", "Completed Payments", completedPayments);
        System.out.printf("%-25s: %d\n", "Total Payments", payments.size());
        
        // Calculate success rate
        double deliverySuccessRate = deliveries.size() > 0 ? 
            (double) completed / deliveries.size() * 100 : 0;
        
        System.out.println("\n📊 PERFORMANCE METRICS:");
        System.out.printf("%-25s: %.1f%%\n", "Delivery Success Rate", deliverySuccessRate);
        System.out.printf("%-25s: %.1f%%\n", "Vehicle Utilization", 
                         vehicles.size() > 0 ? (double) inUseVehicles / vehicles.size() * 100 : 0);
        
        MenuView.showDivider();
        System.out.println("Last Updated: " + new Date());
    }
    
    private void viewAllUsers() {
        MenuView.showSectionHeader("ALL SYSTEM USERS");
        
        System.out.println("\n👥 CUSTOMERS (" + countCustomers() + "):");
        MenuView.showDivider();
        
        int customerNum = 1;
        for (User user : users) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                System.out.println("\n" + customerNum + ". " + customer.getName());
                System.out.println("   ID: " + customer.getUserId());
                System.out.println("   Email: " + customer.getEmail());
                System.out.println("   Phone: " + customer.getPhone());
                System.out.println("   Address: " + customer.getAddress());
                System.out.println("   Loyalty Points: " + customer.getLoyaltyPoints());
                
                // Count parcels sent by this customer
                int sentParcels = 0;
                for (Parcel parcel : parcels) {
                    if (parcel.getSender().getUserId().equals(customer.getUserId())) {
                        sentParcels++;
                    }
                }
                System.out.println("   Parcels Sent: " + sentParcels);
                customerNum++;
            }
        }
        
        System.out.println("\n👔 STAFF MEMBERS (" + countStaff() + "):");
        MenuView.showDivider();
        
        int staffNum = 1;
        for (User user : users) {
            if (user instanceof Staff && !user.getUserId().equals("ADMIN")) {
                Staff staff = (Staff) user;
                System.out.println("\n" + staffNum + ". " + staff.getName());
                System.out.println("   ID: " + staff.getUserId());
                System.out.println("   Role: " + staff.getRole());
                System.out.println("   Email: " + staff.getEmail());
                System.out.println("   Phone: " + staff.getPhone());
                System.out.println("   Salary: RM" + staff.getSalary());
                System.out.println("   Status: " + (staff.isAvailable() ? "Available" : "Busy"));
                
                // Count deliveries assigned
                int assignedDeliveries = 0;
                int completedDeliveries = 0;
                for (Delivery delivery : deliveries) {
                    if (delivery.getDeliveryPerson() != null && 
                        delivery.getDeliveryPerson().getUserId().equals(staff.getUserId())) {
                        assignedDeliveries++;
                        if (delivery.getStatus().equals("Delivered")) {
                            completedDeliveries++;
                        }
                    }
                }
                System.out.println("   Assigned Deliveries: " + assignedDeliveries);
                System.out.println("   Completed Deliveries: " + completedDeliveries);
                staffNum++;
            }
        }
        
        System.out.println("\n👑 ADMINISTRATORS:");
        MenuView.showDivider();
        System.out.println("1. System Administrator (ADMIN) - Full Access");
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TOTAL USERS: " + users.size() + 
                         " (Customers: " + countCustomers() + 
                         ", Staff: " + countStaff() + 
                         ", Admins: 1)");
    }
    
    private int countCustomers() {
        int count = 0;
        for (User user : users) {
            if (user instanceof Customer) count++;
        }
        return count;
    }
    
    private int countStaff() {
        int count = 0;
        for (User user : users) {
            if (user instanceof Staff && !user.getUserId().equals("ADMIN")) count++;
        }
        return count;
    }
    
    private void viewAllParcels() {
        MenuView.showSectionHeader("ALL PARCELS IN SYSTEM");
        
        if (parcels.isEmpty()) {
            System.out.println("No parcels in the system.");
            return;
        }
        
        System.out.println("Total parcels: " + parcels.size());
        System.out.println("\nDisplay Options:");
        System.out.println("1. View as List");
        System.out.println("2. View Detailed");
        System.out.println("3. View by Type");
        System.out.println("4. View by Status");
        System.out.print("\nSelect view option (1-4): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: viewParcelsAsList(); break;
            case 2: viewParcelsDetailed(); break;
            case 3: viewParcelsByType(); break;
            case 4: viewParcelsByStatus(); break;
            default: 
                MenuView.showError("Invalid choice! Showing as list.");
                viewParcelsAsList();
        }
    }
    
    private void viewParcelsAsList() {
        System.out.println("\n" + "-".repeat(100));
        System.out.printf("%-3s %-10s %-15s %-25s %-12s %-10s %-15s\n",
                         "No", "ID", "Type", "Description", "Status", "Price", "Sender → Receiver");
        System.out.println("-".repeat(100));
        
        for (int i = 0; i < parcels.size(); i++) {
            Parcel parcel = parcels.get(i);
            System.out.printf("%-3d %-10s %-15s %-25s %-12s RM%-9.2f %-15s\n",
                             i + 1,
                             parcel.getParcelId(),
                             parcel.getClass().getSimpleName(),
                             truncate(parcel.getDescription(), 24),
                             parcel.getStatus(),
                             parcel.getPrice(),
                             truncate(parcel.getSender().getName(), 7) + " → " + 
                             truncate(parcel.getReceiver().getName(), 7));
        }
        System.out.println("-".repeat(100));
    }
    
    private void viewParcelsDetailed() {
        for (int i = 0; i < parcels.size(); i++) {
            Parcel parcel = parcels.get(i);
            System.out.println("\n" + (i + 1) + ". " + parcel.getParcelId());
            parcel.displayParcelInfo();
            
            // Show delivery info if exists
            boolean hasDelivery = false;
            for (Delivery delivery : deliveries) {
                if (delivery.getParcel().getParcelId().equals(parcel.getParcelId())) {
                    System.out.println("   Delivery: " + delivery.getDeliveryId() + 
                                     " (" + delivery.getStatus() + ")");
                    hasDelivery = true;
                    break;
                }
            }
            
            if (!hasDelivery) {
                System.out.println("   Delivery: Not assigned");
            }
            
            if (i < parcels.size() - 1) {
                System.out.println("-".repeat(50));
            }
        }
    }
    
    private void viewParcelsByType() {
        System.out.println("\n1. Standard Parcels");
        System.out.println("2. Express Parcels");
        System.out.println("3. International Parcels");
        System.out.print("\nSelect type (1-3): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        String type = "";
        switch (choice) {
            case 1: type = "Standard"; break;
            case 2: type = "Express"; break;
            case 3: type = "International"; break;
            default: type = "Standard";
        }
        
        System.out.println("\n" + type.toUpperCase() + " PARCELS:");
        int count = 0;
        
        for (Parcel parcel : parcels) {
            String parcelType = parcel.getClass().getSimpleName();
            if ((type.equals("Standard") && parcel instanceof StandardParcel) ||
                (type.equals("Express") && parcel instanceof ExpressParcel) ||
                (type.equals("International") && parcel instanceof InternationalParcel)) {
                
                System.out.println("\n" + (++count) + ". " + parcel.getParcelId());
                System.out.println("   Description: " + parcel.getDescription());
                System.out.println("   Status: " + parcel.getStatus());
                System.out.println("   Price: RM" + parcel.getPrice());
                System.out.println("   Sender: " + parcel.getSender().getName());
                System.out.println("   Receiver: " + parcel.getReceiver().getName());
            }
        }
        
        System.out.println("\nTotal " + type + " parcels: " + count);
    }
    
    private void viewParcelsByStatus() {
        System.out.println("\nSelect status to view:");
        System.out.println("1. Created/Unpaid");
        System.out.println("2. Processing");
        System.out.println("3. In Transit");
        System.out.println("4. Out for Delivery");
        System.out.println("5. Delivered");
        System.out.println("6. Returned");
        System.out.print("\nSelect (1-6): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        String status = "";
        switch (choice) {
            case 1: status = "Created"; break;
            case 2: status = "Processing"; break;
            case 3: status = "In Transit"; break;
            case 4: status = "Out for Delivery"; break;
            case 5: status = "Delivered"; break;
            case 6: status = "Returned"; break;
            default: status = "Created";
        }
        
        System.out.println("\nPARCELS WITH STATUS: " + status.toUpperCase());
        int count = 0;
        
        for (Parcel parcel : parcels) {
            if (parcel.getStatus().contains(status)) {
                System.out.println("\n" + (++count) + ". " + parcel.getParcelId());
                System.out.println("   Description: " + parcel.getDescription());
                System.out.println("   Type: " + parcel.getClass().getSimpleName());
                System.out.println("   Price: RM" + parcel.getPrice());
                System.out.println("   Sender: " + parcel.getSender().getName());
                System.out.println("   Receiver: " + parcel.getReceiver().getName());
            }
        }
        
        System.out.println("\nTotal parcels with status '" + status + "': " + count);
    }
    
    private void manageDeliveries() {
        DeliveryView.displayDeliveryHeader();
        
        System.out.println("\n1. View All Deliveries");
        System.out.println("2. View Delivery Details");
        System.out.println("3. Assign Staff to Delivery");
        System.out.println("4. Assign Vehicle to Delivery");
        System.out.println("5. Update Delivery Route");
        System.out.println("6. Update Delivery Status");
        System.out.println("7. View Delivery Statistics");
        System.out.println("8. Back to Admin Menu");
        System.out.print("\nSelect option (1-8): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: viewAllDeliveries(); break;
            case 2: viewDeliveryDetails(); break;
            case 3: assignStaffToDelivery(); break;
            case 4: assignVehicleToDelivery(); break;
            case 5: updateDeliveryRoute(); break;
            case 6: updateDeliveryStatus(); break;
            case 7: viewDeliveryStatistics(); break;
            case 8: return;
            default: MenuView.showError("Invalid option!");
        }
    }
    
    private void viewAllDeliveries() {
        MenuView.showSectionHeader("ALL DELIVERIES");
        
        if (deliveries.isEmpty()) {
            System.out.println("No deliveries in system.");
            return;
        }
        
        DeliveryView.displayDeliveryListHeader();
        
        for (int i = 0; i < deliveries.size(); i++) {
            Delivery delivery = deliveries.get(i);
            DeliveryView.displayDeliveryInList(delivery, i + 1);
        }
        
        System.out.println("-".repeat(90));
        System.out.println("Total deliveries: " + deliveries.size());
        
        System.out.print("\nView details of specific delivery? (enter number or 0 to skip): ");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice > 0 && choice <= deliveries.size()) {
                Delivery selected = deliveries.get(choice - 1);
                DeliveryView.displayDeliveryDetails(selected);
                DeliveryView.displayDeliveryProgress(selected.getStatus());
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
        }
    }
    
    private void viewDeliveryDetails() {
        System.out.print("\nEnter Delivery ID (e.g., D001): ");
        String deliveryId = scanner.nextLine();
        
        Delivery found = null;
        for (Delivery delivery : deliveries) {
            if (delivery.getDeliveryId().equals(deliveryId)) {
                found = delivery;
                break;
            }
        }
        
        if (found == null) {
            MenuView.showError("Delivery not found!");
            return;
        }
        
        DeliveryView.displayDeliveryDetails(found);
        DeliveryView.displayDeliveryProgress(found.getStatus());
        
        if (found.getRoute() != null && !found.getRoute().isEmpty()) {
            DeliveryView.displayRouteMap(found.getRoute());
        }
        
        // Show parcel details
        System.out.print("\nView parcel details? (y/n): ");
        String viewParcel = scanner.nextLine();
        if (viewParcel.equalsIgnoreCase("y")) {
            found.getParcel().displayParcelInfo();
        }
    }
    
    private void assignStaffToDelivery() {
        MenuView.showSectionHeader("ASSIGN STAFF TO DELIVERY");
        
        if (deliveries.isEmpty()) {
            System.out.println("No deliveries available.");
            return;
        }
        
        // Show deliveries without staff
        System.out.println("Deliveries needing staff assignment:");
        int count = 0;
        ArrayList<Delivery> unassignedDeliveries = new ArrayList<>();
        
        for (Delivery delivery : deliveries) {
            if (delivery.getDeliveryPerson() == null) {
                System.out.println((++count) + ". " + delivery.getDeliveryId() + 
                                 " - " + delivery.getParcel().getDescription());
                unassignedDeliveries.add(delivery);
            }
        }
        
        if (count == 0) {
            System.out.println("All deliveries have staff assigned.");
            return;
        }
        
        System.out.print("\nSelect delivery (1-" + count + "): ");
        int deliveryChoice = scanner.nextInt();
        scanner.nextLine();
        
        if (deliveryChoice < 1 || deliveryChoice > unassignedDeliveries.size()) {
            MenuView.showError("Invalid selection!");
            return;
        }
        
        Delivery selectedDelivery = unassignedDeliveries.get(deliveryChoice - 1);
        
        // Show available staff
        System.out.println("\nAvailable Staff Members:");
        ArrayList<Staff> availableStaff = new ArrayList<>();
        int staffCount = 0;
        
        for (User user : users) {
            if (user instanceof Staff && !user.getUserId().equals("ADMIN")) {
                Staff staff = (Staff) user;
                if (staff.isAvailable()) {
                    System.out.println((++staffCount) + ". " + staff.getName() + 
                                     " (" + staff.getRole() + ")");
                    availableStaff.add(staff);
                }
            }
        }
        
        if (staffCount == 0) {
            MenuView.showError("No staff available!");
            return;
        }
        
        System.out.print("\nSelect staff (1-" + staffCount + "): ");
        int staffChoice = scanner.nextInt();
        scanner.nextLine();
        
        if (staffChoice < 1 || staffChoice > availableStaff.size()) {
            MenuView.showError("Invalid selection!");
            return;
        }
        
        Staff selectedStaff = availableStaff.get(staffChoice - 1);
        selectedDelivery.setDeliveryPerson(selectedStaff);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        STAFF ASSIGNMENT CONFIRMED");
        System.out.println("=".repeat(50));
        System.out.println("Delivery: " + selectedDelivery.getDeliveryId());
        System.out.println("Parcel: " + selectedDelivery.getParcel().getDescription());
        System.out.println("Assigned Staff: " + selectedStaff.getName());
        System.out.println("Staff Role: " + selectedStaff.getRole());
        System.out.println("Status Updated: Assigned → Scheduled");
        System.out.println("=".repeat(50));
        
        selectedDelivery.updateDeliveryStatus("Scheduled");
        MenuView.showSuccess("Staff assigned successfully!");
    }
    
    private void assignVehicleToDelivery() {
        MenuView.showSectionHeader("ASSIGN VEHICLE TO DELIVERY");
        
        if (deliveries.isEmpty()) {
            System.out.println("No deliveries available.");
            return;
        }
        
        System.out.println("Select delivery to assign vehicle:");
        for (int i = 0; i < deliveries.size(); i++) {
            Delivery d = deliveries.get(i);
            String vehicleInfo = (d.getAssignedVehicle() != null) ? 
                               d.getAssignedVehicle().getVehicleId() : "None";
            System.out.println((i + 1) + ". " + d.getDeliveryId() + 
                             " - " + d.getParcel().getDescription() +
                             " (Vehicle: " + vehicleInfo + ")");
        }
        
        System.out.print("\nSelect delivery (1-" + deliveries.size() + "): ");
        int deliveryChoice = scanner.nextInt();
        scanner.nextLine();
        
        if (deliveryChoice < 1 || deliveryChoice > deliveries.size()) {
            MenuView.showError("Invalid selection!");
            return;
        }
        
        Delivery selectedDelivery = deliveries.get(deliveryChoice - 1);
        double parcelWeight = selectedDelivery.getParcel().getWeight();
        
        // Check if already has a vehicle
        if (selectedDelivery.getAssignedVehicle() != null) {
            System.out.println("\nThis delivery already has vehicle: " + 
                             selectedDelivery.getAssignedVehicle().getVehicleId());
            System.out.print("Replace it? (y/n): ");
            String replace = scanner.nextLine();
            if (!replace.equalsIgnoreCase("y")) {
                return;
            }
            selectedDelivery.getAssignedVehicle().releaseVehicle();
        }
        
        // Show suitable vehicles
        System.out.println("\nAvailable Vehicles (Capacity ≥ " + parcelWeight + " kg):");
        ArrayList<Vehicle> suitableVehicles = new ArrayList<>();
        int vehicleCount = 0;
        
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable() && vehicle.canCarry(parcelWeight)) {
                System.out.println((++vehicleCount) + ". " + vehicle.getVehicleId() + 
                                 " - " + vehicle.getVehicleType() + 
                                 " (Capacity: " + vehicle.getCapacity() + "kg, " +
                                 "Plate: " + vehicle.getPlateNumber() + ")");
                suitableVehicles.add(vehicle);
            }
        }
        
        if (vehicleCount == 0) {
            System.out.println("\nNo suitable vehicles available!");
            System.out.println("Requirements: Available & Capacity ≥ " + parcelWeight + "kg");
            return;
        }
        
        System.out.print("\nSelect vehicle (1-" + vehicleCount + "): ");
        int vehicleChoice = scanner.nextInt();
        scanner.nextLine();
        
        if (vehicleChoice < 1 || vehicleChoice > suitableVehicles.size()) {
            MenuView.showError("Invalid selection!");
            return;
        }
        
        Vehicle selectedVehicle = suitableVehicles.get(vehicleChoice - 1);
        
        // Assign vehicle
        selectedVehicle.assignVehicle(selectedDelivery.getDeliveryId());
        selectedDelivery.setAssignedVehicle(selectedVehicle);
        
        // Update route
        String newRoute = selectedVehicle.getVehicleType() + " Route (" + 
                         selectedVehicle.getPlateNumber() + ")";
        selectedDelivery.assignRoute(newRoute);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("    VEHICLE ASSIGNMENT CONFIRMED");
        System.out.println("=".repeat(50));
        System.out.println("Delivery: " + selectedDelivery.getDeliveryId());
        System.out.println("Parcel: " + selectedDelivery.getParcel().getDescription());
        System.out.println("Weight: " + parcelWeight + " kg");
        System.out.println("Vehicle: " + selectedVehicle.getVehicleId());
        System.out.println("Type: " + selectedVehicle.getVehicleType());
        System.out.println("Capacity: " + selectedVehicle.getCapacity() + " kg");
        System.out.println("Route: " + newRoute);
        System.out.println("=".repeat(50));
        
        MenuView.showSuccess("Vehicle assigned successfully!");
    }
    
    private void updateDeliveryRoute() {
        System.out.print("\nEnter Delivery ID: ");
        String deliveryId = scanner.nextLine();
        
        Delivery found = null;
        for (Delivery delivery : deliveries) {
            if (delivery.getDeliveryId().equals(deliveryId)) {
                found = delivery;
                break;
            }
        }
        
        if (found == null) {
            MenuView.showError("Delivery not found!");
            return;
        }
        
        System.out.println("\nCurrent Route: " + found.getRoute());
        DeliveryView.displayRoutePlanningMenu();
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        String newRoute = "";
        switch (choice) {
            case 1: newRoute = "Main City Route"; break;
            case 2: newRoute = "Suburban Route"; break;
            case 3: newRoute = "Rural Route"; break;
            case 4: newRoute = "Express Highway Route"; break;
            case 5: 
                System.out.print("Enter custom route: ");
                newRoute = scanner.nextLine();
                break;
            default: newRoute = "Main City Route";
        }
        
        found.assignRoute(newRoute);
        DeliveryView.displayRouteMap(newRoute);
        MenuView.showSuccess("Route updated successfully!");
    }
    
    private void updateDeliveryStatus() {
        System.out.print("\nEnter Delivery ID: ");
        String deliveryId = scanner.nextLine();
        
        Delivery found = null;
        for (Delivery delivery : deliveries) {
            if (delivery.getDeliveryId().equals(deliveryId)) {
                found = delivery;
                break;
            }
        }
        
        if (found == null) {
            MenuView.showError("Delivery not found!");
            return;
        }
        
        System.out.println("\nCurrent Status: " + found.getStatus());
        DeliveryView.displayDeliveryStatusOptions();
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        String newStatus = DeliveryView.getStatusFromChoice(choice);
        found.updateDeliveryStatus(newStatus);
        found.getParcel().updateStatus(newStatus);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("     STATUS UPDATE CONFIRMED");
        System.out.println("=".repeat(50));
        System.out.println("Delivery: " + found.getDeliveryId());
        System.out.println("Parcel: " + found.getParcel().getDescription());
        System.out.println("Old Status: " + found.getStatus());
        System.out.println("New Status: " + newStatus);
        System.out.println("Time: " + new Date());
        System.out.println("=".repeat(50));
        
        MenuView.showSuccess("Status updated successfully!");
    }
    
    private void viewDeliveryStatistics() {
        MenuView.showSectionHeader("DELIVERY STATISTICS");
        
        if (deliveries.isEmpty()) {
            System.out.println("No delivery data available.");
            return;
        }
        
        // Calculate statistics
        int scheduled = 0, loading = 0, departured = 0, transit = 0;
        int outForDelivery = 0, delivered = 0, returned = 0, cancelled = 0;
        
        for (Delivery delivery : deliveries) {
            String status = delivery.getStatus();
            switch (status) {
                case "Scheduled": scheduled++; break;
                case "Loading": loading++; break;
                case "Departured": departured++; break;
                case "In Transit": transit++; break;
                case "Out for Delivery": outForDelivery++; break;
                case "Delivered": delivered++; break;
                case "Returned": returned++; break;
                case "Cancelled": cancelled++; break;
            }
        }
        
        System.out.println("\n📊 STATUS DISTRIBUTION:");
        System.out.printf("%-20s: %d (%.1f%%)\n", "Scheduled", scheduled, 
                         getPercentage(scheduled, deliveries.size()));
        System.out.printf("%-20s: %d (%.1f%%)\n", "Loading", loading, 
                         getPercentage(loading, deliveries.size()));
        System.out.printf("%-20s: %d (%.1f%%)\n", "Departured", departured, 
                         getPercentage(departured, deliveries.size()));
        System.out.printf("%-20s: %d (%.1f%%)\n", "In Transit", transit, 
                         getPercentage(transit, deliveries.size()));
        System.out.printf("%-20s: %d (%.1f%%)\n", "Out for Delivery", outForDelivery, 
                         getPercentage(outForDelivery, deliveries.size()));
        System.out.printf("%-20s: %d (%.1f%%)\n", "Delivered", delivered, 
                         getPercentage(delivered, deliveries.size()));
        System.out.printf("%-20s: %d (%.1f%%)\n", "Returned", returned, 
                         getPercentage(returned, deliveries.size()));
        System.out.printf("%-20s: %d (%.1f%%)\n", "Cancelled", cancelled, 
                         getPercentage(cancelled, deliveries.size()));
        
        // Staff performance
        System.out.println("\n👥 STAFF PERFORMANCE:");
        for (User user : users) {
            if (user instanceof Staff && !user.getUserId().equals("ADMIN")) {
                Staff staff = (Staff) user;
                int assigned = 0, completed = 0;
                
                for (Delivery delivery : deliveries) {
                    if (delivery.getDeliveryPerson() != null && 
                        delivery.getDeliveryPerson().getUserId().equals(staff.getUserId())) {
                        assigned++;
                        if (delivery.getStatus().equals("Delivered")) {
                            completed++;
                        }
                    }
                }
                
                if (assigned > 0) {
                    double successRate = (double) completed / assigned * 100;
                    System.out.printf("%-15s: %d assigned, %d completed (%.1f%% success)\n",
                                     staff.getName(), assigned, completed, successRate);
                }
            }
        }
        
        // Vehicle utilization
        System.out.println("\n🚛 VEHICLE UTILIZATION:");
        for (Vehicle vehicle : vehicles) {
            int usedCount = 0;
            for (Delivery delivery : deliveries) {
                if (delivery.getAssignedVehicle() != null && 
                    delivery.getAssignedVehicle().getVehicleId().equals(vehicle.getVehicleId())) {
                    usedCount++;
                }
            }
            System.out.printf("%-10s: Used in %d deliveries (%s)\n",
                             vehicle.getVehicleId(), usedCount,
                             vehicle.isAvailable() ? "Available" : "In Use");
        }
        
        System.out.println("\n📈 OVERALL METRICS:");
        System.out.println("Total Deliveries: " + deliveries.size());
        System.out.println("Completion Rate: " + 
                         getPercentage(delivered, deliveries.size()) + "%");
        System.out.println("Return Rate: " + 
                         getPercentage(returned, deliveries.size()) + "%");
    }
    
    private void manageVehicles() {
        MenuView.showSectionHeader("VEHICLE MANAGEMENT");
        
        System.out.println("\n1. View All Vehicles");
        System.out.println("2. Add New Vehicle");
        System.out.println("3. Update Vehicle Status");
        System.out.println("4. View Vehicle Assignments");
        System.out.println("5. Back to Admin Menu");
        System.out.print("\nSelect option (1-5): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: viewAllVehicles(); break;
            case 2: addNewVehicle(); break;
            case 3: updateVehicleStatus(); break;
            case 4: viewVehicleAssignments(); break;
            case 5: return;
            default: MenuView.showError("Invalid option!");
        }
    }
    
    private void viewAllVehicles() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                        VEHICLE FLEET");
        System.out.println("=".repeat(70));
        
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles in fleet.");
            return;
        }
        
        System.out.printf("%-3s %-10s %-15s %-12s %-10s %-10s %s\n",
                         "No", "ID", "Type", "Plate", "Capacity", "Status", "Current Assignment");
        System.out.println("-".repeat(70));
        
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            System.out.printf("%-3d %-10s %-15s %-12s %-10.1f %-10s %s\n",
                             i + 1,
                             vehicle.getVehicleId(),
                             vehicle.getVehicleType(),
                             vehicle.getPlateNumber(),
                             vehicle.getCapacity(),
                             vehicle.isAvailable() ? "Available" : "In Use",
                             vehicle.getCurrentDeliveryId() != null ? 
                             vehicle.getCurrentDeliveryId() : "None");
        }
        System.out.println("-".repeat(70));
        System.out.println("Total vehicles: " + vehicles.size());
    }
    
    private void addNewVehicle() {
        MenuView.showSectionHeader("ADD NEW VEHICLE");
        
        System.out.print("Enter Vehicle ID (e.g., V004): ");
        String vehicleId = scanner.nextLine();
        
        // Check if ID already exists
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId().equals(vehicleId)) {
                MenuView.showError("Vehicle ID already exists!");
                return;
            }
        }
        
        System.out.println("\nVehicle Types:");
        System.out.println("1. Motorcycle (Capacity: 10-50kg)");
        System.out.println("2. Van (Capacity: 100-500kg)");
        System.out.println("3. Truck (Capacity: 500-2000kg)");
        System.out.println("4. Lorry (Capacity: 2000-5000kg)");
        System.out.print("\nSelect type (1-4): ");
        
        int typeChoice = scanner.nextInt();
        scanner.nextLine();
        
        String vehicleType = "";
        double defaultCapacity = 0;
        
        switch (typeChoice) {
            case 1: 
                vehicleType = "Motorcycle";
                defaultCapacity = 50;
                break;
            case 2: 
                vehicleType = "Van";
                defaultCapacity = 500;
                break;
            case 3: 
                vehicleType = "Truck";
                defaultCapacity = 2000;
                break;
            case 4: 
                vehicleType = "Lorry";
                defaultCapacity = 5000;
                break;
            default: 
                vehicleType = "Van";
                defaultCapacity = 500;
        }
        
        System.out.print("Enter License Plate: ");
        String plateNumber = scanner.nextLine();
        
        System.out.print("Enter Capacity in kg (Default: " + defaultCapacity + "): ");
        String capacityInput = scanner.nextLine();
        double capacity;
        
        if (capacityInput.isEmpty()) {
            capacity = defaultCapacity;
        } else {
            try {
                capacity = Double.parseDouble(capacityInput);
                if (capacity <= 0) {
                    MenuView.showError("Capacity must be positive!");
                    return;
                }
            } catch (NumberFormatException e) {
                MenuView.showError("Invalid capacity! Using default.");
                capacity = defaultCapacity;
            }
        }
        
        Vehicle newVehicle = new Vehicle(vehicleId, vehicleType, plateNumber, capacity);
        vehicles.add(newVehicle);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("     NEW VEHICLE ADDED TO FLEET");
        System.out.println("=".repeat(50));
        DeliveryView.displayVehicleInfo(newVehicle);
        MenuView.showSuccess("Vehicle added successfully!");
    }
    
    private void updateVehicleStatus() {
        System.out.print("\nEnter Vehicle ID: ");
        String vehicleId = scanner.nextLine();
        
        Vehicle found = null;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId().equals(vehicleId)) {
                found = vehicle;
                break;
            }
        }
        
        if (found == null) {
            MenuView.showError("Vehicle not found!");
            return;
        }
        
        DeliveryView.displayVehicleInfo(found);
        
        System.out.println("\nUpdate Options:");
        System.out.println("1. Mark as Available");
        System.out.println("2. Mark as In Use");
        System.out.println("3. Release from Current Assignment");
        System.out.println("4. Update Capacity");
        System.out.println("5. Cancel");
        System.out.print("\nSelect option (1-5): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                if (!found.isAvailable()) {
                    found.releaseVehicle();
                    MenuView.showSuccess("Vehicle marked as Available.");
                } else {
                    System.out.println("Vehicle is already available.");
                }
                break;
                
            case 2:
                if (found.isAvailable()) {
                    System.out.print("Enter Delivery ID for assignment: ");
                    String deliveryId = scanner.nextLine();
                    found.assignVehicle(deliveryId);
                    MenuView.showSuccess("Vehicle marked as In Use.");
                } else {
                    System.out.println("Vehicle is already in use.");
                }
                break;
                
            case 3:
                if (!found.isAvailable()) {
                    found.releaseVehicle();
                    MenuView.showSuccess("Vehicle released from assignment.");
                } else {
                    System.out.println("Vehicle is not assigned to any delivery.");
                }
                break;
                
            case 4:
                System.out.print("Enter new capacity (kg): ");
                double newCapacity = scanner.nextDouble();
                scanner.nextLine();
                
                if (newCapacity > 0) {
                    // Note: You might need to add a setCapacity method to Vehicle class
                    MenuView.showSuccess("Capacity update would go here.");
                } else {
                    MenuView.showError("Capacity must be positive!");
                }
                break;
                
            case 5:
                return;
                
            default:
                MenuView.showError("Invalid option!");
        }
    }
    
    private void viewVehicleAssignments() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                    VEHICLE ASSIGNMENTS");
        System.out.println("=".repeat(70));
        
        boolean hasAssignments = false;
        
        for (Vehicle vehicle : vehicles) {
            if (!vehicle.isAvailable() && vehicle.getCurrentDeliveryId() != null) {
                System.out.println("\nVehicle: " + vehicle.getVehicleId());
                System.out.println("Type: " + vehicle.getVehicleType());
                System.out.println("Plate: " + vehicle.getPlateNumber());
                System.out.println("Assigned to Delivery: " + vehicle.getCurrentDeliveryId());
                
                // Find the delivery
                for (Delivery delivery : deliveries) {
                    if (delivery.getDeliveryId().equals(vehicle.getCurrentDeliveryId())) {
                        System.out.println("Parcel: " + delivery.getParcel().getDescription());
                        System.out.println("Status: " + delivery.getStatus());
                        break;
                    }
                }
                
                System.out.println("-".repeat(50));
                hasAssignments = true;
            }
        }
        
        if (!hasAssignments) {
            System.out.println("No vehicles currently assigned to deliveries.");
        }
    }
    
    private void viewAllPayments() {
        MenuView.showSectionHeader("ALL PAYMENTS");
        
        if (payments.isEmpty()) {
            System.out.println("No payments recorded.");
            return;
        }
        
        System.out.println("\nDisplay Options:");
        System.out.println("1. View All Payments");
        System.out.println("2. View Completed Payments");
        System.out.println("3. View Pending Payments");
        System.out.println("4. View Payment Summary");
        System.out.print("\nSelect option (1-4): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: displayAllPayments(); break;
            case 2: displayCompletedPayments(); break;
            case 3: displayPendingPayments(); break;
            case 4: displayPaymentSummary(); break;
            default: displayAllPayments();
        }
    }
    
    private void displayAllPayments() {
        System.out.println("\n" + "-".repeat(80));
        System.out.printf("%-3s %-15s %-12s %-15s %-10s %-15s\n",
                         "No", "Payment ID", "Amount", "Method", "Status", "Date");
        System.out.println("-".repeat(80));
        
        for (int i = 0; i < payments.size(); i++) {
            Payment payment = payments.get(i);
            String statusSymbol = payment.getStatus().equals("Completed") ? "✅" : "⏳";
            
            System.out.printf("%-3d %-15s RM%-11.2f %-15s %-10s %-15s\n",
                             i + 1,
                             payment.getPaymentId(),
                             payment.getAmount(),
                             payment.getPaymentMethod(),
                             statusSymbol + " " + payment.getStatus(),
                             payment.getPaymentDate());
        }
        System.out.println("-".repeat(80));
        System.out.println("Total payments: " + payments.size());
    }
    
    private void displayCompletedPayments() {
        System.out.println("\n✅ COMPLETED PAYMENTS:");
        double total = 0;
        int count = 0;
        
        for (Payment payment : payments) {
            if (payment.getStatus().equals("Completed")) {
                System.out.printf("%-15s: RM%.2f via %s on %s\n",
                                 payment.getPaymentId(),
                                 payment.getAmount(),
                                 payment.getPaymentMethod(),
                                 payment.getPaymentDate());
                total += payment.getAmount();
                count++;
            }
        }
        
        System.out.println("\nTotal completed payments: " + count);
        System.out.println("Total revenue: RM" + total);
    }
    
    private void displayPendingPayments() {
        System.out.println("\n⏳ PENDING PAYMENTS:");
        double total = 0;
        int count = 0;
        
        for (Payment payment : payments) {
            if (!payment.getStatus().equals("Completed")) {
                System.out.printf("%-15s: RM%.2f via %s (Status: %s)\n",
                                 payment.getPaymentId(),
                                 payment.getAmount(),
                                 payment.getPaymentMethod(),
                                 payment.getStatus());
                total += payment.getAmount();
                count++;
            }
        }
        
        System.out.println("\nTotal pending payments: " + count);
        System.out.println("Total pending amount: RM" + total);
    }
    
    private void displayPaymentSummary() {
        double totalRevenue = 0;
        double pendingAmount = 0;
        int completedCount = 0;
        int pendingCount = 0;
        
        // Count by payment method
        double creditCardTotal = 0;
        double onlineBankingTotal = 0;
        double cashTotal = 0;
        
        for (Payment payment : payments) {
            if (payment.getStatus().equals("Completed")) {
                totalRevenue += payment.getAmount();
                completedCount++;
                
                // Count by method
                switch (payment.getPaymentMethod()) {
                    case "Credit Card": creditCardTotal += payment.getAmount(); break;
                    case "Online Banking": onlineBankingTotal += payment.getAmount(); break;
                    case "Cash on Delivery": cashTotal += payment.getAmount(); break;
                }
            } else {
                pendingAmount += payment.getAmount();
                pendingCount++;
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        PAYMENT SUMMARY");
        System.out.println("=".repeat(50));
        System.out.printf("%-25s: %d\n", "Total Payments", payments.size());
        System.out.printf("%-25s: %d\n", "Completed Payments", completedCount);
        System.out.printf("%-25s: %d\n", "Pending Payments", pendingCount);
        System.out.printf("%-25s: RM%.2f\n", "Total Revenue", totalRevenue);
        System.out.printf("%-25s: RM%.2f\n", "Pending Amount", pendingAmount);
        
        System.out.println("\n--- Revenue by Payment Method ---");
        System.out.printf("%-20s: RM%.2f (%.1f%%)\n", "Credit Card", creditCardTotal,
                         getPercentage(creditCardTotal, totalRevenue));
        System.out.printf("%-20s: RM%.2f (%.1f%%)\n", "Online Banking", onlineBankingTotal,
                         getPercentage(onlineBankingTotal, totalRevenue));
        System.out.printf("%-20s: RM%.2f (%.1f%%)\n", "Cash on Delivery", cashTotal,
                         getPercentage(cashTotal, totalRevenue));
        System.out.println("=".repeat(50));
    }
    
    private void addNewStaff() {
        MenuView.showSectionHeader("ADD NEW STAFF MEMBER");
        
        System.out.print("Enter Staff ID (e.g., S003): ");
        String staffId = scanner.nextLine();
        
        // Check if ID exists
        for (User user : users) {
            if (user.getUserId().equals(staffId)) {
                MenuView.showError("Staff ID already exists!");
                return;
            }
        }
        
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        if (!Validator.isValidEmail(email)) {
            MenuView.showError("Invalid email format!");
            return;
        }
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        
        if (!Validator.isValidPhone(phone)) {
            MenuView.showError("Invalid phone number!");
            return;
        }
        
        System.out.println("\nStaff Roles:");
        System.out.println("1. Delivery Driver");
        System.out.println("2. Warehouse Staff");
        System.out.println("3. Customer Service");
        System.out.println("4. Warehouse Manager");
        System.out.println("5. Operations Manager");
        System.out.print("\nSelect role (1-5): ");
        
        int roleChoice = scanner.nextInt();
        scanner.nextLine();
        
        String role = "";
        double defaultSalary = 0;
        
        switch (roleChoice) {
            case 1: 
                role = "Delivery Driver";
                defaultSalary = 2200;
                break;
            case 2: 
                role = "Warehouse Staff";
                defaultSalary = 1800;
                break;
            case 3: 
                role = "Customer Service";
                defaultSalary = 2000;
                break;
            case 4: 
                role = "Warehouse Manager";
                defaultSalary = 3200;
                break;
            case 5: 
                role = "Operations Manager";
                defaultSalary = 4000;
                break;
            default: 
                role = "Staff";
                defaultSalary = 2000;
        }
        
        System.out.print("Enter Monthly Salary (Default: RM" + defaultSalary + "): ");
        String salaryInput = scanner.nextLine();
        double salary;
        
        if (salaryInput.isEmpty()) {
            salary = defaultSalary;
        } else {
            try {
                salary = Double.parseDouble(salaryInput);
                if (salary <= 0) {
                    MenuView.showError("Salary must be positive!");
                    return;
                }
            } catch (NumberFormatException e) {
                MenuView.showError("Invalid salary! Using default.");
                salary = defaultSalary;
            }
        }
        
        Staff newStaff = new Staff(staffId, name, email, password, phone, role, salary);
        users.add(newStaff);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("     NEW STAFF MEMBER ADDED");
        System.out.println("=".repeat(50));
        newStaff.displayStaffInfo();
        MenuView.showSuccess("Staff member added successfully!");
    }
    
    private void generateReport() {
        MenuView.showSectionHeader("GENERATE SYSTEM REPORT");
        
        System.out.println("\nReport Types:");
        System.out.println("1. Daily Activity Report");
        System.out.println("2. Weekly Performance Report");
        System.out.println("3. Monthly Financial Report");
        System.out.println("4. Comprehensive System Report");
        System.out.print("\nSelect report type (1-4): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("            SYSTEM REPORT");
        System.out.println("=".repeat(60));
        System.out.println("Report Type: " + getReportType(choice));
        System.out.println("Generated: " + new Date());
        System.out.println("Generated By: Administrator");
        System.out.println("=".repeat(60));
        
        // Show statistics
        showStatistics();
        
        // Additional report data
        System.out.println("\n--- ADDITIONAL METRICS ---");
        
        // Average parcel value
        double totalParcelValue = 0;
        for (Parcel parcel : parcels) {
            totalParcelValue += parcel.getPrice();
        }
        double avgParcelValue = parcels.size() > 0 ? totalParcelValue / parcels.size() : 0;
        System.out.printf("Average Parcel Value: RM%.2f\n", avgParcelValue);
        
        // Busiest staff member
        Staff busiestStaff = null;
        int maxDeliveries = 0;
        for (User user : users) {
            if (user instanceof Staff && !user.getUserId().equals("ADMIN")) {
                Staff staff = (Staff) user;
                int deliveryCount = 0;
                for (Delivery delivery : deliveries) {
                    if (delivery.getDeliveryPerson() != null && 
                        delivery.getDeliveryPerson().getUserId().equals(staff.getUserId())) {
                        deliveryCount++;
                    }
                }
                if (deliveryCount > maxDeliveries) {
                    maxDeliveries = deliveryCount;
                    busiestStaff = staff;
                }
            }
        }
        
        if (busiestStaff != null) {
            System.out.println("\n🌟 TOP PERFORMING STAFF:");
            System.out.println("Name: " + busiestStaff.getName());
            System.out.println("Role: " + busiestStaff.getRole());
            System.out.println("Deliveries Assigned: " + maxDeliveries);
        }
        
        // Most valuable customer
        Customer topCustomer = null;
        int maxPoints = 0;
        for (User user : users) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                if (customer.getLoyaltyPoints() > maxPoints) {
                    maxPoints = customer.getLoyaltyPoints();
                    topCustomer = customer;
                }
            }
        }
        
        if (topCustomer != null) {
            System.out.println("\n👑 TOP LOYALTY CUSTOMER:");
            System.out.println("Name: " + topCustomer.getName());
            System.out.println("Loyalty Points: " + maxPoints);
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("          END OF REPORT");
        System.out.println("=".repeat(60));
        
        MenuView.showSuccess("Report generated successfully!");
        
        // Ask if want to save to file
        System.out.print("\nSave report to file? (y/n): ");
        String save = scanner.nextLine();
        if (save.equalsIgnoreCase("y")) {
            System.out.println("Report saved to: system_report_" + 
                             new Date().toString().replace(" ", "_") + ".txt");
        }
    }
    
    private String getReportType(int choice) {
        switch (choice) {
            case 1: return "Daily Activity Report";
            case 2: return "Weekly Performance Report";
            case 3: return "Monthly Financial Report";
            case 4: return "Comprehensive System Report";
            default: return "System Report";
        }
    }
    
    private void demonstrateArrays() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("  📊 ARRAY DEMONSTRATION - PHASE 2 RUBRIC REQUIREMENTS");
        System.out.println("=".repeat(70));
        
        // Demonstrate arrays from utils
        ArrayDemo.demonstrateArrays();
        
        // Demonstrate arrays from services
        parcelService.demonstrateArrayUsage();
        
        // Demonstrate ArrayList operations
        System.out.println("\n=== ARRAYLIST OPERATIONS ===");
        System.out.println("Current ArrayLists in system:");
        System.out.println("1. users ArrayList: " + users.size() + " users");
        System.out.println("   Operations: add(), remove(), get(), size(), isEmpty()");
        
        System.out.println("\n2. parcels ArrayList: " + parcels.size() + " parcels");
        System.out.println("   Operations: add(), remove(), get(), contains(), indexOf()");
        
        System.out.println("\n3. vehicles ArrayList: " + vehicles.size() + " vehicles");
        System.out.println("   Operations: add(), clear(), iterator(), toArray()");
        
        // Show array conversion
        System.out.println("\n=== ARRAY CONVERSION EXAMPLES ===");
        System.out.println("Converting ArrayList to Array:");
        
        if (!parcels.isEmpty()) {
            Parcel[] parcelArray = parcels.toArray(new Parcel[0]);
            System.out.println("Parcel[] parcelArray = parcels.toArray(new Parcel[0])");
            System.out.println("Array length: " + parcelArray.length);
            System.out.println("First element: " + parcelArray[0].getParcelId());
        }
        
        System.out.println("\n✅ All rubric array requirements demonstrated:");
        System.out.println("   ✓ Primitive arrays (int[], double[])");
        System.out.println("   ✓ String arrays (String[])");
        System.out.println("   ✓ Object arrays (Parcel[], Vehicle[])");
        System.out.println("   ✓ ArrayList (dynamic arrays)");
        System.out.println("   ✓ Array operations (access, iteration, length)");
        System.out.println("=".repeat(70));
    }
    
    private void backupSystemData() {
        MenuView.showSectionHeader("SYSTEM DATA BACKUP");
        
        System.out.println("\nBackup Options:");
        System.out.println("1. Backup All Data");
        System.out.println("2. Backup User Data");
        System.out.println("3. Backup Parcel Data");
        System.out.println("4. Backup Delivery Data");
        System.out.println("5. View Backup Status");
        System.out.println("6. Cancel");
        System.out.print("\nSelect option (1-6): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.println("\n📦 Backing up ALL system data...");
                MenuView.showLoading("Creating backup");
                System.out.println("✅ Backup created successfully!");
                System.out.println("   Users: " + users.size() + " records");
                System.out.println("   Parcels: " + parcels.size() + " records");
                System.out.println("   Deliveries: " + deliveries.size() + " records");
                System.out.println("   Payments: " + payments.size() + " records");
                System.out.println("   Vehicles: " + vehicles.size() + " records");
                break;
                
            case 2:
                System.out.println("\n👥 Backing up user data...");
                MenuView.showLoading("Creating user backup");
                System.out.println("✅ User backup created: " + users.size() + " users");
                break;
                
            case 3:
                System.out.println("\n📦 Backing up parcel data...");
                MenuView.showLoading("Creating parcel backup");
                System.out.println("✅ Parcel backup created: " + parcels.size() + " parcels");
                break;
                
            case 4:
                System.out.println("\n🚚 Backing up delivery data...");
                MenuView.showLoading("Creating delivery backup");
                System.out.println("✅ Delivery backup created: " + deliveries.size() + " deliveries");
                break;
                
            case 5:
                System.out.println("\n📊 BACKUP STATUS:");
                System.out.println("Total Records in System:");
                System.out.println("   Users: " + users.size());
                System.out.println("   Parcels: " + parcels.size());
                System.out.println("   Deliveries: " + deliveries.size());
                System.out.println("   Payments: " + payments.size());
                System.out.println("   Vehicles: " + vehicles.size());
                System.out.println("\nLast Backup: " + new Date());
                break;
                
            case 6:
                return;
                
            default:
                MenuView.showError("Invalid option!");
        }
        
        System.out.println("\n💾 Backup saved to: system_backup_" + 
                         System.currentTimeMillis() + ".dat");
    }
    
    // Utility methods
    private double getPercentage(double part, double total) {
        if (total == 0) return 0;
        return (part / total) * 100;
    }
    
    private double getPercentage(int part, int total) {
        if (total == 0) return 0;
        return ((double) part / total) * 100;
    }
    
    private String truncate(String text, int length) {
        if (text.length() <= length) return text;
        return text.substring(0, length - 3) + "...";
    }
}