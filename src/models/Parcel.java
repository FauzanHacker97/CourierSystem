package models;

public class Parcel {
    private String parcelId;
    private String description;
    private double weight;
    private String status;
    private Customer sender;
    private Staff assignedStaff;
    private String destination;
    private String recipientName;
    private String recipientPhone;
    private String[] trackingHistory;
    private int historyCount;
    
    public Parcel(String parcelId, String description, double weight, 
                  String status, Customer sender, 
                  String destination, String recipientName, 
                  String recipientPhone) {
        this.parcelId = parcelId;
        this.description = description;
        this.weight = weight;
        this.status = status;
        this.sender = sender;
        this.destination = destination;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.assignedStaff = null;
        this.trackingHistory = new String[10];
        this.historyCount = 0;
        addToHistory("Parcel created - Status: " + status);
    }
    
    // Getter methods
    public String getParcelId() {
        return parcelId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
        addToHistory("Status updated to: " + status);
    }
    
    public Customer getSender() {
        return sender;
    }
    
    public Staff getAssignedStaff() {
        return assignedStaff;
    }
    
    public void setAssignedStaff(Staff staff) {
        this.assignedStaff = staff;
        addToHistory("Assigned to staff: " + staff.getName());
    }
    
    // NEW: Destination getters
    public String getDestination() {
        return destination;
    }
    
    public String getRecipientName() {
        return recipientName;  // THIS ONE WAS MISSING!
    }
    
    public String getRecipientPhone() {
        return recipientPhone;
    }
    
    // Optional setters for destination
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    
    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }
    
    // Method to add tracking history
    private void addToHistory(String event) {
        if (historyCount < trackingHistory.length) {
            trackingHistory[historyCount] = event;
            historyCount++;
        }
    }
    
    // Method to display tracking history
    public void displayTrackingHistory() {
        System.out.println("\n--- Tracking History for Parcel " + parcelId + " ---");
        for (int i = 0; i < historyCount; i++) {
            System.out.println((i + 1) + ". " + trackingHistory[i]);
        }
    }
    
    // Method to display parcel details
    public void displayDetails() {
        System.out.println("\n=== Parcel Details ===");
        System.out.println("Parcel ID: " + parcelId);
        System.out.println("Description: " + description);
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Status: " + status);
        System.out.println("Sender: " + sender.getName());
        System.out.println("Destination: " + destination);
        System.out.println("Recipient: " + recipientName);
        System.out.println("Recipient Phone: " + recipientPhone);
        
        if (assignedStaff != null) {
            System.out.println("Assigned Staff: " + assignedStaff.getName());
        }
    }
}