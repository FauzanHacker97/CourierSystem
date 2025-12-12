package models;

import java.time.LocalDate;

public abstract class Parcel {
    protected String parcelId;
    protected User sender;
    protected User receiver;
    protected double weight;
    protected String dimensions;
    protected String status;
    protected double price;
    protected LocalDate createdDate;
    protected String description;
    
    public Parcel(String parcelId, User sender, User receiver, 
                  double weight, String dimensions, String description) {
        this.parcelId = parcelId;
        this.sender = sender;
        this.receiver = receiver;
        this.weight = weight;
        this.dimensions = dimensions;
        this.description = description;
        this.status = "Created";
        this.createdDate = LocalDate.now();
        this.price = calculatePrice(); // Abstract method call
    }
    
    // Abstract methods (polymorphism)
    public abstract double calculatePrice();
    public abstract void updateStatus(String newStatus);
    
    // Common methods
    public String getParcelId() { return parcelId; }
    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
    public double getWeight() { return weight; }
    public String getDimensions() { return dimensions; }
    public String getStatus() { return status; }
    public double getPrice() { return price; }
    public LocalDate getCreatedDate() { return createdDate; }
    public String getDescription() { return description; }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void displayParcelInfo() {
        System.out.println("\n=== PARCEL INFORMATION ===");
        System.out.println("Parcel ID: " + parcelId);
        System.out.println("Description: " + description);
        System.out.println("Sender: " + sender.getName());
        System.out.println("Receiver: " + receiver.getName());
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Dimensions: " + dimensions);
        System.out.println("Status: " + status);
        System.out.println("Price: RM" + price);
        System.out.println("Created: " + createdDate);
    }
}