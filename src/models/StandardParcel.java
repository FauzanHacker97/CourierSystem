package models;

public class StandardParcel extends Parcel {
    private int maxDeliveryDays = 7;
    private double maxWeight = 30.0;
    
    public StandardParcel(String parcelId, User sender, User receiver,
                         double weight, String dimensions, String description) {
        super(parcelId, sender, receiver, weight, dimensions, description);
    }
    
    @Override
    public double calculatePrice() {
        // Standard pricing: base + per kg
        double basePrice = 8.0;
        double perKgPrice = 2.5;
        return basePrice + (weight * perKgPrice);
    }
    
    @Override
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Standard Parcel " + parcelId + " status: " + newStatus);
    }
    
    public int getMaxDeliveryDays() {
        return maxDeliveryDays;
    }
    
    public double getMaxWeight() {
        return maxWeight;
    }
    
    @Override
    public void displayParcelInfo() {
        super.displayParcelInfo();
        System.out.println("Type: Standard Parcel");
        System.out.println("Max Delivery Days: " + maxDeliveryDays);
        System.out.println("Max Weight: " + maxWeight + " kg");
    }
}