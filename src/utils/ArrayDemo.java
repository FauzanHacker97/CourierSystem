package utils;

import models.Parcel;

public class ArrayDemo {
    
    // Primitive arrays (for rubric)
    private static final double[] SHIPPING_RATES = {5.0, 8.0, 12.0, 15.0, 20.0, 25.0};
    private static final int[] DELIVERY_DAYS = {1, 2, 3, 5, 7, 10};
    private static final String[] COUNTRIES = {"Malaysia", "Singapore", "Thailand", 
                                               "Indonesia", "Philippines", "Vietnam"};
    
    // Object array
    private Parcel[] parcelArchive = new Parcel[100];
    private int archiveCount = 0;
    
    public static void demonstrateArrays() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ARRAY DEMONSTRATION (For OOP Rubric)");
        System.out.println("=".repeat(50));
        
        // 1. Primitive array - double[]
        System.out.println("\n1. PRIMITIVE ARRAY (double[] SHIPPING_RATES):");
        System.out.println("   These are the standard shipping rates:");
        for (int i = 0; i < SHIPPING_RATES.length; i++) {
            System.out.printf("   Rate %d: RM%.2f\n", i + 1, SHIPPING_RATES[i]);
        }
        
        // 2. Primitive array - int[]
        System.out.println("\n2. PRIMITIVE ARRAY (int[] DELIVERY_DAYS):");
        System.out.println("   Standard delivery days for different services:");
        for (int i = 0; i < DELIVERY_DAYS.length; i++) {
            System.out.println("   Service " + (i + 1) + ": " + DELIVERY_DAYS[i] + " days");
        }
        
        // 3. String array
        System.out.println("\n3. STRING ARRAY (String[] COUNTRIES):");
        System.out.println("   Countries we deliver to:");
        for (String country : COUNTRIES) {
            System.out.println("   - " + country);
        }
        
        // 4. Array operations
        System.out.println("\n4. ARRAY OPERATIONS:");
        System.out.println("   SHIPPING_RATES length: " + SHIPPING_RATES.length);
        System.out.println("   First shipping rate: RM" + SHIPPING_RATES[0]);
        System.out.println("   Last shipping rate: RM" + SHIPPING_RATES[SHIPPING_RATES.length - 1]);
        System.out.println("   Total countries: " + COUNTRIES.length);
        
        // 5. Multi-dimensional array example
        System.out.println("\n5. MULTI-DIMENSIONAL ARRAY:");
        double[][] zoneRates = {
            {5.0, 8.0, 12.0},   // Zone 1 rates
            {8.0, 12.0, 18.0},  // Zone 2 rates
            {12.0, 18.0, 25.0}  // Zone 3 rates
        };
        
        System.out.println("   Zone-based shipping rates:");
        for (int i = 0; i < zoneRates.length; i++) {
            System.out.print("   Zone " + (i + 1) + ": ");
            for (int j = 0; j < zoneRates[i].length; j++) {
                System.out.printf("RM%.1f ", zoneRates[i][j]);
            }
            System.out.println();
        }
    }
    
    // Add parcel to archive array
    public void addToArchive(Parcel parcel) {
        if (archiveCount < parcelArchive.length) {
            parcelArchive[archiveCount] = parcel;
            archiveCount++;
        }
    }
    
    // Display archive contents
    public void displayArchive() {
        System.out.println("\n=== PARCEL ARCHIVE (Object Array) ===");
        System.out.println("Array size: " + parcelArchive.length);
        System.out.println("Current count: " + archiveCount);
        
        if (archiveCount > 0) {
            System.out.println("\nFirst 5 parcels in archive:");
            for (int i = 0; i < Math.min(5, archiveCount); i++) {
                System.out.println((i + 1) + ". " + parcelArchive[i].getParcelId() + 
                                 " - " + parcelArchive[i].getClass().getSimpleName());
            }
        }
    }
    
    // Get archive array
    public Parcel[] getParcelArchive() {
        return parcelArchive;
    }
    
    // Get archive count
    public int getArchiveCount() {
        return archiveCount;
    }
}