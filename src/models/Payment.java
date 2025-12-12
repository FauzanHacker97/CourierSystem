package models;

import java.time.LocalDate;

public class Payment {
    private String paymentId;
    private Parcel parcel;
    private double amount;
    private String paymentMethod;
    private LocalDate paymentDate;
    private String status;
    
    public Payment(String paymentId, Parcel parcel, double amount, 
                   String paymentMethod) {
        this.paymentId = paymentId;
        this.parcel = parcel;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = LocalDate.now();
        this.status = "Pending";
    }
    
    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }
    
    public Parcel getParcel() {
        return parcel;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void processPayment() {
        this.status = "Completed";
        System.out.println("Payment " + paymentId + " processed successfully!");
    }
    
    public void displayPaymentDetails() {
        System.out.println("\n=== Payment Details ===");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Amount: RM" + amount);
        System.out.println("Method: " + paymentMethod);
        System.out.println("Date: " + paymentDate);
        System.out.println("Status: " + status);
        System.out.println("Parcel ID: " + parcel.getParcelId());
    }
}