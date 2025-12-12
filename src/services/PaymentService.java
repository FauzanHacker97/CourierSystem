package services;

import models.Payment;
import models.Parcel;
import java.util.ArrayList;

public class PaymentService {
    private ArrayList<Payment> payments;
    
    public PaymentService() {
        payments = new ArrayList<>();
    }
    
    // Create new payment
    public Payment createPayment(Parcel parcel, double amount, String method) {
        String paymentId = "PAY" + System.currentTimeMillis();
        Payment payment = new Payment(paymentId, parcel, amount, method);
        payments.add(payment);
        return payment;
    }
    
    // Process payment
    public void processPayment(String paymentId) {
        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(paymentId)) {
                payment.processPayment();
                return;
            }
        }
        System.out.println("Payment not found!");
    }
    
    // Display all payments
    public void displayAllPayments() {
        System.out.println("\n=== ALL PAYMENTS ===");
        for (Payment payment : payments) {
            System.out.println(payment.getPaymentId() + " - RM" + 
                             payment.getAmount() + " - " + payment.getStatus());
        }
    }
    
    // Calculate total revenue
    public double calculateTotalRevenue() {
        double total = 0;
        for (Payment payment : payments) {
            if (payment.getStatus().equals("Completed")) {
                total += payment.getAmount();
            }
        }
        return total;
    }
}