package models;

import java.time.LocalDate;

public class Payment {
    private String paymentId;
    private double amount;
    private String paymentMethod;
    private String status;
    private LocalDate paymentDate;
    
    public Payment(String paymentId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = "Pending";
        this.paymentDate = LocalDate.now();
    }
    
    // Process payment
    public void processPayment() {
        this.status = "Completed";
        System.out.println("Payment " + paymentId + " processed successfully!");
    }
    
    // Getters
    public String getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }
    public LocalDate getPaymentDate() { return paymentDate; }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void displayPaymentInfo() {
        System.out.println("\n=== PAYMENT INFORMATION ===");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Amount: RM" + amount);
        System.out.println("Method: " + paymentMethod);
        System.out.println("Status: " + status);
        System.out.println("Date: " + paymentDate);
    }
}