package models;

import java.time.LocalDateTime;

public class ExpressParcel extends Parcel {
    private double urgentFee;
    private LocalDateTime guaranteedTime;
    
    public ExpressParcel(String parcelId, User sender, User receiver,
                        double weight, String dimensions, String description) {
        super(parcelId, sender, receiver, weight, dimensions, description);
        this.urgentFee = calculateUrgentFee();
        this.guaranteedTime = calculateGuaranteedTime();
    }
    
    @Override
    public double calculatePrice() {
        // Express pricing: higher base + per kg + urgent fee
        double basePrice = 15.0;
        double perKgPrice = 4.0;
        return basePrice + (weight * perKgPrice) + urgentFee;
    }
    
    @Override
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Express Parcel " + parcelId + " status: " + newStatus);
    }
    
    private double calculateUrgentFee() {
        return 10.0 + (weight * 1.5);
    }
    
    private LocalDateTime calculateGuaranteedTime() {
        return LocalDateTime.now().plusHours(24); // 24-hour guarantee
    }
    
    public double getUrgentFee() {
        return urgentFee;
    }
    
    public LocalDateTime getGuaranteedTime() {
        return guaranteedTime;
    }
    
    @Override
    public void displayParcelInfo() {
        super.displayParcelInfo();
        System.out.println("Type: Express Parcel");
        System.out.println("Urgent Fee: RM" + urgentFee);
        System.out.println("Guaranteed Delivery: " + guaranteedTime);
    }
}