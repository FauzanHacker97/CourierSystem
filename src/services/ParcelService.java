package services;

import models.Parcel;
import java.util.ArrayList;

public class ParcelService {
    
    // Array of objects (for rubric requirement)
    private Parcel[] parcelArray;
    private int parcelCount;
    
    // Array of primitives (for rubric requirement)
    private double[] weightCategories = {0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 30.0};
    private String[] statusOptions = {"Created", "Processing", "In Transit", "Out for Delivery", "Delivered", "Returned"};
    
    public ParcelService() {
        parcelArray = new Parcel[100];
        parcelCount = 0;
    }
    
    // Add parcel to array
    public void addParcel(Parcel parcel) {
        if (parcelCount < parcelArray.length) {
            parcelArray[parcelCount] = parcel;
            parcelCount++;
            System.out.println("Parcel added to array. Total in array: " + parcelCount);
        } else {
            System.out.println("Parcel array is full!");
        }
    }
    
    // Find parcel by ID
    public Parcel findParcelById(String parcelId) {
        for (int i = 0; i < parcelCount; i++) {
            if (parcelArray[i].getParcelId().equals(parcelId)) {
                return parcelArray[i];
            }
        }
        return null;
    }
    
    // Update parcel status
    public boolean updateParcelStatus(String parcelId, String newStatus) {
        Parcel parcel = findParcelById(parcelId);
        if (parcel != null) {
            parcel.updateStatus(newStatus);
            return true;
        }
        return false;
    }
    
    // Demonstrate array usage (for rubric)
    public void demonstrateArrayUsage() {
        System.out.println("\n=== ParcelService Array Demonstration ===");
        
        // 1. Primitive array demonstration
        System.out.println("1. Primitive Array (double[] weightCategories):");
        System.out.println("   Index | Weight Limit (kg)");
        for (int i = 0; i < weightCategories.length; i++) {
            System.out.printf("   [%d]   | %.1f kg\n", i, weightCategories[i]);
        }
        
        // 2. String array demonstration
        System.out.println("\n2. String Array (String[] statusOptions):");
        for (int i = 0; i < statusOptions.length; i++) {
            System.out.println("   " + (i + 1) + ". " + statusOptions[i]);
        }
        
        // 3. Object array demonstration
        System.out.println("\n3. Object Array (Parcel[] parcelArray):");
        System.out.println("   Array size: " + parcelArray.length);
        System.out.println("   Current count: " + parcelCount);
        
        if (parcelCount > 0) {
            System.out.println("\n   First 3 parcels in array:");
            for (int i = 0; i < Math.min(3, parcelCount); i++) {
                System.out.println("   - " + parcelArray[i].getParcelId() + 
                                 " (" + parcelArray[i].getClass().getSimpleName() + ")");
            }
        }
        
        // 4. Array operations
        System.out.println("\n4. Array Operations:");
        if (parcelCount > 0) {
            System.out.println("   First element: " + parcelArray[0].getParcelId());
            System.out.println("   Last element: " + parcelArray[parcelCount - 1].getParcelId());
            System.out.println("   Array length: " + parcelArray.length);
        }
    }
    
    // Count parcels by status
    public int countParcelsByStatus(String status) {
        int count = 0;
        for (int i = 0; i < parcelCount; i++) {
            if (parcelArray[i].getStatus().equalsIgnoreCase(status)) {
                count++;
            }
        }
        return count;
    }
    
    // Get parcel count in array
    public int getParcelCount() {
        return parcelCount;
    }
    
    // Get parcel array
    public Parcel[] getParcelArray() {
        return parcelArray;
    }
}