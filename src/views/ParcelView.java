package views;

import models.Parcel;

public class ParcelView {
    
    public static void displayCompactParcelList(Parcel parcel, int index) {
        System.out.printf("%-3d. %-10s %-15s %-20s %-12s RM%-8.2f\n",
                         index,
                         parcel.getParcelId(),
                         parcel.getClass().getSimpleName(),
                         truncate(parcel.getDescription(), 18),
                         parcel.getStatus(),
                         parcel.getPrice());
    }
    
    public static void displayParcelTableHeader() {
        System.out.println("\n" + "-".repeat(80));
        System.out.printf("%-3s %-10s %-15s %-20s %-12s %-10s\n",
                         "No", "ID", "Type", "Description", "Status", "Price");
        System.out.println("-".repeat(80));
    }
    
    private static String truncate(String text, int length) {
        if (text.length() <= length) return text;
        return text.substring(0, length - 3) + "...";
    }
}