package services;

import models.Payment;
import java.util.ArrayList;

public class PaymentService {
    private ArrayList<Payment> payments;
    
    public PaymentService() {
        payments = new ArrayList<>();
    }
    
    // Create new payment
    public Payment createPayment(double amount, String method) {
        String paymentId = "PAY" + System.currentTimeMillis();
        Payment payment = new Payment(paymentId, amount, method);
        payments.add(payment);
        return payment;
    }
    
    // Process payment by ID
    public void processPayment(String paymentId) {
        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(paymentId)) {
                payment.processPayment();
                return;
            }
        }
        System.out.println("Payment not found!");
    }
    
    // Get total revenue
    public double calculateTotalRevenue() {
        double total = 0;
        for (Payment payment : payments) {
            if (payment.getStatus().equals("Completed")) {
                total += payment.getAmount();
            }
        }
        return total;
    }
    
    // Display all payments
    public void displayAllPayments() {
        System.out.println("\n=== ALL PAYMENTS ===");
        for (Payment payment : payments) {
            System.out.println(payment.getPaymentId() + " - RM" + 
                             payment.getAmount() + " - " + payment.getStatus());
        }
    }
    
    // Get payments list
    public ArrayList<Payment> getPayments() {
        return payments;
    }
    
    // Get payment by ID
    public Payment getPaymentById(String paymentId) {
        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }
}