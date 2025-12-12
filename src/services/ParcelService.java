package services;

import models.Parcel;
import models.Staff;
import java.util.ArrayList;

public class ParcelService {
    
    // Array of objects
    private Parcel[] parcelArray;
    private int parcelCount;
    
    public ParcelService() {
        parcelArray = new Parcel[50];
        parcelCount = 0;
    }
    
    // Add parcel to array
    public void addParcel(Parcel parcel) {
        if (parcelCount < parcelArray.length) {
            parcelArray[parcelCount] = parcel;
            parcelCount++;
            System.out.println("Parcel added successfully!");
        } else {
            System.out.println("Parcel storage is full!");
        }
    }
    
    // Find parcel by ID using linear search
    public Parcel findParcelById(String parcelId) {
        for (int i = 0; i < parcelCount; i++) {
            if (parcelArray[i].getParcelId().equals(parcelId)) {
                return parcelArray[i];
            }
        }
        return null;
    }
    
    // Display all parcels
    public void displayAllParcels() {
        System.out.println("\n=== ALL PARCELS ===");
        for (int i = 0; i < parcelCount; i++) {
            System.out.println((i + 1) + ". " + parcelArray[i].getParcelId() + 
                             " - " + parcelArray[i].getDescription() + 
                             " (" + parcelArray[i].getStatus() + ")");
        }
    }
    
    // Update parcel status
    public boolean updateParcelStatus(String parcelId, String newStatus) {
        Parcel parcel = findParcelById(parcelId);
        if (parcel != null) {
            parcel.setStatus(newStatus);
            System.out.println("Parcel status updated!");
            return true;
        }
        System.out.println("Parcel not found!");
        return false;
    }
    
    // Assign staff to parcel
    public boolean assignStaffToParcel(String parcelId, Staff staff) {
        Parcel parcel = findParcelById(parcelId);
        if (parcel != null) {
            parcel.setAssignedStaff(staff);
            staff.incrementParcelsDelivered();
            return true;
        }
        return false;
    }
    
    // Count parcels by status
  // In ParcelService.java
public int countParcelsByStatus(String status) {
    int count = 0;
    for (int i = 0; i < parcelCount; i++) {
        // Use contains for flexible matching
        if (parcelArray[i].getStatus().toLowerCase().contains(status.toLowerCase())) {
            count++;
        }
    }
    return count;
}
}