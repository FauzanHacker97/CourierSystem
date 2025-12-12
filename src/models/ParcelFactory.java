package models;

public class ParcelFactory {
    
    // Factory method to create different types of parcels
    public static Parcel createParcel(String parcelType, String parcelId, 
                                     User sender, User receiver,
                                     double weight, String dimensions,
                                     String description, String additionalInfo) {
        
        switch(parcelType.toUpperCase()) {
            case "STANDARD":
                return new StandardParcel(parcelId, sender, receiver, 
                                        weight, dimensions, description);
                
            case "EXPRESS":
                return new ExpressParcel(parcelId, sender, receiver, 
                                       weight, dimensions, description);
                
            case "INTERNATIONAL":
                return new InternationalParcel(parcelId, sender, receiver, 
                                             weight, dimensions, description, additionalInfo);
                
            default:
                System.out.println("Unknown parcel type. Creating Standard parcel.");
                return new StandardParcel(parcelId, sender, receiver, 
                                        weight, dimensions, description);
        }
    }
    
    // Method to display available parcel types
    public static void displayParcelTypes() {
        System.out.println("\n=== AVAILABLE PARCEL TYPES ===");
        System.out.println("1. STANDARD - Regular delivery (3-7 days)");
        System.out.println("   Base: RM8.00 + RM2.50/kg");
        System.out.println("2. EXPRESS - Fast delivery (24 hours guarantee)");
        System.out.println("   Base: RM15.00 + RM4.00/kg + urgent fee");
        System.out.println("3. INTERNATIONAL - Overseas delivery");
        System.out.println("   Base: RM25.00 + RM8.00/kg + customs fee");
        System.out.println("\nUse the type names exactly as shown above.");
    }
}