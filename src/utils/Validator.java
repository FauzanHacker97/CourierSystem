package utils;

public class Validator {
    
    // String methods demonstration
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return email.contains("@") && email.contains(".");
    }
    
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) return false;
        
        // Remove any spaces or dashes
        String cleaned = phone.replaceAll("[\\s-]", "");
        
        // Check if it contains only digits and has 10-11 digits
        return cleaned.matches("\\d{10,11}");
    }
    
    public static boolean isValidWeight(double weight) {
        return weight > 0 && weight <= 100; // Max 100kg
    }
    
    // Method to format phone number
    public static String formatPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) return "";
        
        // String manipulation methods
        String cleaned = phone.replaceAll("[^0-9]", "");
        
        if (cleaned.length() == 10) {
            return cleaned.substring(0, 3) + "-" + 
                   cleaned.substring(3, 6) + "-" + 
                   cleaned.substring(6);
        } else if (cleaned.length() == 11) {
            return cleaned.substring(0, 3) + "-" + 
                   cleaned.substring(3, 7) + "-" + 
                   cleaned.substring(7);
        }
        return phone;
    }
    
    // Validate parcel dimensions
    public static boolean isValidDimensions(String dimensions) {
        if (dimensions == null || dimensions.isEmpty()) return false;
        return dimensions.matches("\\d+x\\d+x\\d+"); // Format: LxWxH
    }
}