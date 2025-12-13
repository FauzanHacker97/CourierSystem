package controllers;

import models.*;
import views.*;
import java.util.*;

public class StaffController {
    private Scanner scanner;
    private ArrayList<Delivery> deliveries;
    private ArrayList<Parcel> parcels;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<User> users;
    
    public StaffController(Scanner scanner, ArrayList<Delivery> deliveries,
                          ArrayList<Parcel> parcels, ArrayList<Vehicle> vehicles,
                          ArrayList<User> users) {
        this.scanner = scanner;
        this.deliveries = deliveries;
        this.parcels = parcels;
        this.vehicles = vehicles;
        this.users = users;
    }
    
    public void showMenu(Staff staff) {
        boolean logout = false;
        
        while (!logout) {
            MenuView.showSectionHeader("STAFF DASHBOARD - " + staff.getName());
            System.out.println("Role: " + staff.getRole());
            System.out.println("Status: " + (staff.isAvailable() ? "Available" : "Busy"));
            MenuView.showDivider();
            
            System.out.println("1. View Assigned Deliveries");
            System.out.println("2. Update Parcel Status");
            System.out.println("3. Mark Delivery Complete");
            System.out.println("4. View Available Vehicles");
            System.out.println("5. Toggle Availability");
            System.out.println("6. View All Parcels");
            System.out.println("7. Logout");
            System.out.print("\nSelect option (1-7): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1: viewAssignedDeliveries(staff); break;
                    case 2: updateParcelStatus(); break;
                    case 3: markDeliveryComplete(staff); break;
                    case 4: viewAvailableVehicles(); break;
                    case 5: toggleAvailability(staff); break;
                    case 6: viewAllParcels(); break;
                    case 7: 
                        logout = true;
                        staff.logout();
                        MenuView.showSuccess("Staff logged out successfully!");
                        break;
                    default: 
                        MenuView.showError("Invalid option! Choose 1-7");
                }
            } catch (InputMismatchException e) {
                MenuView.showError("Please enter a number (1-7)!");
                scanner.nextLine();
            }
        }
    }
    
    private void viewAssignedDeliveries(Staff staff) {
    DeliveryView.displayDeliveryHeader();
    
    boolean hasDeliveries = false;
    int count = 1;
    
    DeliveryView.displayDeliveryListHeader();
    
    for (Delivery delivery : deliveries) {
        if (delivery.getDeliveryPerson() != null && 
            delivery.getDeliveryPerson().getUserId().equals(staff.getUserId())) {
            
            DeliveryView.displayDeliveryInList(delivery, count);
            hasDeliveries = true;
            count++;
        }
    }
    
    if (!hasDeliveries) {
        System.out.println("No deliveries assigned to you.");
    } else {
        System.out.println("-".repeat(90));
        System.out.print("\nView details of specific delivery? (enter number or 0 to skip): ");
        
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice > 0 && choice < count) {
                // Find the delivery
                Delivery selected = null;
                int current = 1;
                for (Delivery delivery : deliveries) {
                    if (delivery.getDeliveryPerson() != null && 
                        delivery.getDeliveryPerson().getUserId().equals(staff.getUserId())) {
                        if (current == choice) {
                            selected = delivery;
                            break;
                        }
                        current++;
                    }
                }
                
                if (selected != null) {
                    DeliveryView.displayDeliveryDetails(selected);
                    DeliveryView.displayDeliveryProgress(selected.getStatus());
                    
                    if (selected.getRoute() != null && !selected.getRoute().isEmpty()) {
                        DeliveryView.displayRouteMap(selected.getRoute());
                    }
                }
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
        }
    }
}
    
    private void updateParcelStatus() {
        MenuView.showSectionHeader("UPDATE PARCEL STATUS");
        
        System.out.print("Enter Parcel ID: ");
        String parcelId = scanner.nextLine();
        
        Parcel parcel = findParcelById(parcelId);
        if (parcel == null) {
            MenuView.showError("Parcel not found!");
            return;
        }
        
        System.out.println("\nCurrent Status: " + parcel.getStatus());
        System.out.println("\nSelect new status:");
        System.out.println("1. Processing");
        System.out.println("2. In Transit");
        System.out.println("3. Out for Delivery");
        System.out.println("4. Delivered");
        System.out.println("5. Returned");
        System.out.print("\nChoice (1-5): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        String newStatus = "";
        switch (choice) {
            case 1: newStatus = "Processing"; break;
            case 2: newStatus = "In Transit"; break;
            case 3: newStatus = "Out for Delivery"; break;
            case 4: newStatus = "Delivered"; break;
            case 5: newStatus = "Returned"; break;
            default: 
                MenuView.showError("Invalid choice!");
                return;
        }
        
        // POLYMORPHISM: updateStatus() behaves differently for each parcel type
        parcel.updateStatus(newStatus);
        MenuView.showSuccess("Status updated to: " + newStatus);
        
        // Update corresponding delivery if exists
        for (Delivery delivery : deliveries) {
            if (delivery.getParcel().getParcelId().equals(parcelId)) {
                delivery.updateDeliveryStatus(newStatus);
            }
        }
    }
    
    private Parcel findParcelById(String id) {
        for (Parcel parcel : parcels) {
            if (parcel.getParcelId().equals(id)) {
                return parcel;
            }
        }
        return null;
    }
    
    private void markDeliveryComplete(Staff staff) {
        MenuView.showSectionHeader("MARK DELIVERY COMPLETE");
        
        // Find deliveries assigned to this staff with "Out for Delivery" status
        ArrayList<Delivery> readyDeliveries = new ArrayList<>();
        int count = 1;
        
        System.out.println("Deliveries ready for completion:");
        for (Delivery delivery : deliveries) {
            if (delivery.getDeliveryPerson() != null && 
                delivery.getDeliveryPerson().getUserId().equals(staff.getUserId()) &&
                delivery.getStatus().equals("Out for Delivery")) {
                
                System.out.println(count + ". Delivery " + delivery.getDeliveryId());
                System.out.println("   Parcel: " + delivery.getParcel().getDescription());
                System.out.println("   Recipient: " + delivery.getParcel().getReceiver().getName());
                readyDeliveries.add(delivery);
                count++;
            }
        }
        
        if (readyDeliveries.isEmpty()) {
            System.out.println("No deliveries ready for completion.");
            return;
        }
        
        System.out.print("\nSelect delivery (1-" + readyDeliveries.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice < 1 || choice > readyDeliveries.size()) {
            MenuView.showError("Invalid selection!");
            return;
        }
        
        Delivery selectedDelivery = readyDeliveries.get(choice - 1);
        
        System.out.print("Enter recipient signature/name: ");
        String signature = scanner.nextLine();
        
        selectedDelivery.updateDeliveryStatus("Delivered");
        selectedDelivery.getParcel().updateStatus("Delivered");
        
        // Release vehicle if assigned
        if (selectedDelivery.getAssignedVehicle() != null) {
            selectedDelivery.getAssignedVehicle().releaseVehicle();
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("    DELIVERY COMPLETION RECORD");
        System.out.println("=".repeat(50));
        System.out.println("Delivery ID: " + selectedDelivery.getDeliveryId());
        System.out.println("Parcel: " + selectedDelivery.getParcel().getDescription());
        System.out.println("Recipient: " + selectedDelivery.getParcel().getReceiver().getName());
        System.out.println("Signature: " + signature);
        System.out.println("Staff: " + staff.getName());
        System.out.println("Time: " + new Date());
        System.out.println("=".repeat(50));
        
        MenuView.showSuccess("Delivery marked as complete!");
    }
    
    private void viewAvailableVehicles() {
        MenuView.showSectionHeader("AVAILABLE VEHICLES");
        
        boolean hasAvailable = false;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable()) {
                System.out.println("\nVehicle ID: " + vehicle.getVehicleId());
                System.out.println("Type: " + vehicle.getVehicleType());
                System.out.println("Plate: " + vehicle.getPlateNumber());
                System.out.println("Capacity: " + vehicle.getCapacity() + " kg");
                hasAvailable = true;
            }
        }
        
        if (!hasAvailable) {
            System.out.println("No vehicles currently available.");
        }
    }
    
    private void toggleAvailability(Staff staff) {
        boolean current = staff.isAvailable();
        staff.setAvailable(!current);
        
        if (staff.isAvailable()) {
            MenuView.showSuccess("You are now AVAILABLE for new assignments.");
        } else {
            MenuView.showSuccess("You are now UNAVAILABLE. No new assignments will be given.");
        }
    }
    
    private void viewAllParcels() {
        MenuView.showSectionHeader("ALL PARCELS IN SYSTEM");
        
        if (parcels.isEmpty()) {
            System.out.println("No parcels in the system.");
            return;
        }
        
        System.out.println("Total parcels: " + parcels.size());
        System.out.println("\nParcel List:");
        
        for (int i = 0; i < parcels.size(); i++) {
            Parcel parcel = parcels.get(i);
            System.out.println((i + 1) + ". " + parcel.getParcelId() + 
                             " - " + parcel.getDescription() + 
                             " (" + parcel.getStatus() + ")");
        }
    }
}