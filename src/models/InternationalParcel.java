package models;

public class InternationalParcel extends Parcel {
    private double customsFee;
    private String destinationCountry;
    
    public InternationalParcel(String parcelId, User sender, User receiver,
                              double weight, String dimensions, 
                              String description, String destinationCountry) {
        super(parcelId, sender, receiver, weight, dimensions, description);
        this.destinationCountry = destinationCountry;
        this.customsFee = calculateCustomsFee();
    }
    
    @Override
    public double calculatePrice() {
        // International pricing: high base + per kg + customs
        double basePrice = 25.0;
        double perKgPrice = 8.0;
        return basePrice + (weight * perKgPrice) + customsFee;
    }
    
    @Override
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("International Parcel " + parcelId + " status: " + newStatus);
    }
    
    private double calculateCustomsFee() {
        // Simple customs calculation
        return weight * 5.0;
    }
    
    public double getCustomsFee() {
        return customsFee;
    }
    
    public String getDestinationCountry() {
        return destinationCountry;
    }
    
    @Override
    public void displayParcelInfo() {
        super.displayParcelInfo();
        System.out.println("Type: International Parcel");
        System.out.println("Destination: " + destinationCountry);
        System.out.println("Customs Fee: RM" + customsFee);
    }
}