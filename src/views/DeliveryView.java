package views;

import models.Delivery;
import models.Vehicle;

public class DeliveryView {
    
    public static void displayDeliveryHeader() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                   DELIVERY MANAGEMENT");
        System.out.println("=".repeat(60));
    }
    
    public static void displayDeliveryDetails(Delivery delivery) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           DELIVERY DETAILS");
        System.out.println("=".repeat(50));
        
        System.out.printf("%-20s: %s\n", "Delivery ID", delivery.getDeliveryId());
        System.out.printf("%-20s: %s\n", "Parcel ID", delivery.getParcel().getParcelId());
        System.out.printf("%-20s: %s\n", "Description", delivery.getParcel().getDescription());
        
        if (delivery.getDeliveryPerson() != null) {
            System.out.printf("%-20s: %s\n", "Assigned Staff", delivery.getDeliveryPerson().getName());
            System.out.printf("%-20s: %s\n", "Staff Role", ((models.Staff)delivery.getDeliveryPerson()).getRole());
        } else {
            System.out.printf("%-20s: %s\n", "Assigned Staff", "Not assigned");
        }
        
        if (delivery.getAssignedVehicle() != null) {
            Vehicle vehicle = delivery.getAssignedVehicle();
            System.out.printf("%-20s: %s\n", "Vehicle", vehicle.getVehicleId());
            System.out.printf("%-20s: %s\n", "Vehicle Type", vehicle.getVehicleType());
            System.out.printf("%-20s: %s\n", "License Plate", vehicle.getPlateNumber());
        } else {
            System.out.printf("%-20s: %s\n", "Vehicle", "Not assigned");
        }
        
        System.out.printf("%-20s: %s\n", "Status", delivery.getStatus());
        System.out.printf("%-20s: %s\n", "Route", delivery.getRoute());
        System.out.printf("%-20s: %s\n", "Est. Delivery", delivery.getEstimatedTime());
        System.out.println("=".repeat(50));
    }
    
    public static void displayDeliveryListHeader() {
        System.out.println("\n" + "-".repeat(90));
        System.out.printf("%-3s %-12s %-15s %-20s %-15s %-12s\n",
                         "No", "Delivery ID", "Parcel", "Staff", "Vehicle", "Status");
        System.out.println("-".repeat(90));
    }
    
    public static void displayDeliveryInList(Delivery delivery, int index) {
        String staffName = (delivery.getDeliveryPerson() != null) ? 
                          delivery.getDeliveryPerson().getName() : "Unassigned";
        
        String vehicleInfo = (delivery.getAssignedVehicle() != null) ? 
                           delivery.getAssignedVehicle().getVehicleId() : "None";
        
        System.out.printf("%-3d %-12s %-15s %-20s %-15s %-12s\n",
                         index,
                         delivery.getDeliveryId(),
                         truncate(delivery.getParcel().getDescription(), 14),
                         truncate(staffName, 18),
                         vehicleInfo,
                         delivery.getStatus());
    }
    
    public static void displayVehicleAssignmentMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        VEHICLE ASSIGNMENT");
        System.out.println("=".repeat(50));
        System.out.println("1. Assign Van (Capacity: 500kg)");
        System.out.println("2. Assign Motorcycle (Capacity: 50kg)");
        System.out.println("3. Assign Truck (Capacity: 2000kg)");
        System.out.println("4. View Available Vehicles");
        System.out.println("5. Cancel Assignment");
        System.out.print("\nSelect option (1-5): ");
    }
    
    public static void displayRoutePlanningMenu() {
        System.out.println("\n--- ROUTE PLANNING ---");
        System.out.println("1. Main City Route");
        System.out.println("2. Suburban Route");
        System.out.println("3. Rural Route");
        System.out.println("4. Express Highway Route");
        System.out.println("5. Custom Route");
        System.out.print("\nSelect route type (1-5): ");
    }
    
    public static void displayDeliveryStatusOptions() {
        System.out.println("\n--- DELIVERY STATUS ---");
        System.out.println("1. Scheduled");
        System.out.println("2. Loading");
        System.out.println("3. Departured");
        System.out.println("4. In Transit");
        System.out.println("5. Out for Delivery");
        System.out.println("6. Delivered");
        System.out.println("7. Returned");
        System.out.println("8. Cancelled");
        System.out.print("\nSelect status (1-8): ");
    }
    
    public static String getStatusFromChoice(int choice) {
        switch (choice) {
            case 1: return "Scheduled";
            case 2: return "Loading";
            case 3: return "Departured";
            case 4: return "In Transit";
            case 5: return "Out for Delivery";
            case 6: return "Delivered";
            case 7: return "Returned";
            case 8: return "Cancelled";
            default: return "Unknown";
        }
    }
    
    public static void displayCompletionForm() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        DELIVERY COMPLETION FORM");
        System.out.println("=".repeat(50));
        System.out.println("Please collect the following information:");
    }
    
    public static void displaySignatureBox() {
        System.out.println("\n" + "+" + "-".repeat(48) + "+");
        System.out.println("|          RECIPIENT SIGNATURE           |");
        System.out.println("|" + " ".repeat(48) + "|");
        System.out.println("|                                          |");
        System.out.println("|   _______________________________      |");
        System.out.println("|                                          |");
        System.out.println("|   Name: ___________________________    |");
        System.out.println("|                                          |");
        System.out.println("|   Date: ___________________________    |");
        System.out.println("|                                          |");
        System.out.println("|   Time: ___________________________    |");
        System.out.println("|                                          |");
        System.out.println("+" + "-".repeat(48) + "+");
    }
    
    public static void displayDeliveryReceipt(Delivery delivery, String signature, 
                                            String notes, String completionTime) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("            DELIVERY COMPLETION RECEIPT");
        System.out.println("=".repeat(60));
        
        System.out.printf("%-25s: %s\n", "Delivery ID", delivery.getDeliveryId());
        System.out.printf("%-25s: %s\n", "Parcel ID", delivery.getParcel().getParcelId());
        System.out.printf("%-25s: %s\n", "Description", delivery.getParcel().getDescription());
        System.out.printf("%-25s: %s\n", "Recipient", delivery.getParcel().getReceiver().getName());
        
        if (delivery.getDeliveryPerson() != null) {
            System.out.printf("%-25s: %s\n", "Delivery Staff", delivery.getDeliveryPerson().getName());
        }
        
        if (delivery.getAssignedVehicle() != null) {
            System.out.printf("%-25s: %s\n", "Vehicle", delivery.getAssignedVehicle().getVehicleId());
        }
        
        System.out.printf("%-25s: %s\n", "Signature", signature);
        System.out.printf("%-25s: %s\n", "Completion Time", completionTime);
        
        if (notes != null && !notes.isEmpty()) {
            System.out.printf("%-25s: %s\n", "Delivery Notes", notes);
        }
        
        System.out.printf("%-25s: %s\n", "Status", "✅ DELIVERED");
        System.out.println("=".repeat(60));
    }
    
    public static void displayVehicleInfo(models.Vehicle vehicle) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           VEHICLE INFORMATION");
        System.out.println("=".repeat(50));
        
        System.out.printf("%-20s: %s\n", "Vehicle ID", vehicle.getVehicleId());
        System.out.printf("%-20s: %s\n", "Type", vehicle.getVehicleType());
        System.out.printf("%-20s: %s\n", "License Plate", vehicle.getPlateNumber());
        System.out.printf("%-20s: %.1f kg\n", "Capacity", vehicle.getCapacity());
        System.out.printf("%-20s: %s\n", "Status", 
                         vehicle.isAvailable() ? "✅ Available" : "⛔ In Use");
        
        if (!vehicle.isAvailable() && vehicle.getCurrentDeliveryId() != null) {
            System.out.printf("%-20s: %s\n", "Current Delivery", vehicle.getCurrentDeliveryId());
        }
        
        System.out.println("=".repeat(50));
    }
    
    public static void displayRouteMap(String route) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("              ROUTE MAP");
        System.out.println("=".repeat(50));
        
        if (route.contains("Main City")) {
            System.out.println("🚚 Route: Main City Delivery");
            System.out.println("📍 Stops:");
            System.out.println("   1. Warehouse → KLCC");
            System.out.println("   2. KLCC → Pavilion");
            System.out.println("   3. Pavilion → Mid Valley");
            System.out.println("   4. Mid Valley → Central Market");
            System.out.println("   5. Return to Warehouse");
            System.out.println("📏 Distance: 45km");
            System.out.println("⏱️ Estimated Time: 3-4 hours");
            
        } else if (route.contains("Suburban")) {
            System.out.println("🚚 Route: Suburban Delivery");
            System.out.println("📍 Stops:");
            System.out.println("   1. Warehouse → Petaling Jaya");
            System.out.println("   2. Petaling Jaya → Subang Jaya");
            System.out.println("   3. Subang Jaya → Shah Alam");
            System.out.println("   4. Shah Alam → Klang");
            System.out.println("   5. Return to Warehouse");
            System.out.println("📏 Distance: 85km");
            System.out.println("⏱️ Estimated Time: 5-6 hours");
            
        } else if (route.contains("Rural")) {
            System.out.println("🚚 Route: Rural Area Delivery");
            System.out.println("📍 Stops:");
            System.out.println("   1. Warehouse → Rawang");
            System.out.println("   2. Rawang → Kuala Selangor");
            System.out.println("   3. Kuala Selangor → Sabak Bernam");
            System.out.println("   4. Return to Warehouse");
            System.out.println("📏 Distance: 150km");
            System.out.println("⏱️ Estimated Time: 6-8 hours");
            
        } else {
            System.out.println("📍 Route: " + route);
        }
        
        System.out.println("=".repeat(50));
    }
    
   public static void displayDeliveryProgress(String status) {
    System.out.println("\n--- DELIVERY PROGRESS ---");
    
    String[] steps = {"Scheduled", "Processing", "Loaded", "In Transit", "Out for Delivery", "Delivered"};
    String currentStep = status;
    
    // Find the index of current step
    int currentIndex = -1;
    for (int i = 0; i < steps.length; i++) {
        if (steps[i].equals(currentStep)) {
            currentIndex = i;
            break;
        }
    }
    
    // If current status not in our predefined steps, show custom message
    if (currentIndex == -1) {
        System.out.println("Current Status: " + status);
        System.out.println("(Custom status tracking)");
        return;
    }
    
    for (int i = 0; i < steps.length; i++) {
        if (i == currentIndex) {
            System.out.println("👉 " + steps[i] + " (Current)");
        } else if (i < currentIndex) {
            System.out.println("✅ " + steps[i] + " (Completed)");
        } else {
            System.out.println("○ " + steps[i] + " (Pending)");
        }
    }
}
    
    public static void displayEmergencyContact() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        EMERGENCY CONTACT");
        System.out.println("=".repeat(50));
        System.out.println("🚨 For delivery issues or emergencies:");
        System.out.println("📞 Hotline: 1-300-88-9999");
        System.out.println("📧 Email: support@courier.com");
        System.out.println("🕒 Operating Hours: 8AM - 8PM Daily");
        System.out.println("=".repeat(50));
    }
    
    private static String truncate(String text, int length) {
        if (text.length() <= length) return text;
        return text.substring(0, length - 3) + "...";
    }
}