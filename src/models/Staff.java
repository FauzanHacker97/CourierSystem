package models;

public class Staff extends Person {
    private String department;
    private int parcelsDelivered;
    
    public Staff(String id, String name, String department, String phone) {
        super(id, name, phone);
        this.department = department;
        this.parcelsDelivered = 0;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public int getParcelsDelivered() {
        return parcelsDelivered;
    }
    
    public void incrementParcelsDelivered() {
        parcelsDelivered++;
    }
    
    // Performance rating method
    public String getPerformanceRating() {
        if (parcelsDelivered >= 50) return "Excellent";
        else if (parcelsDelivered >= 30) return "Very Good";
        else if (parcelsDelivered >= 15) return "Good";
        else if (parcelsDelivered >= 5) return "Fair";
        else return "Beginner";
    }
    
    // Formatted display method - CORRECTED VERSION
    public void displayFormattedInfo() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│         STAFF INFORMATION        │");
        System.out.println("├─────────────────────────────────┤");
        System.out.printf("│ ID: %-26s │\n", getId());  // Use getter!
        System.out.printf("│ Name: %-24s │\n", getName());  // Use getter!
        System.out.printf("│ Department: %-19s │\n", department);
        System.out.printf("│ Phone: %-24s │\n", getPhone());  // Use getter!
        System.out.printf("│ Parcels Delivered: %-12d │\n", parcelsDelivered);
        System.out.printf("│ Performance: %-18s │\n", getPerformanceRating());
        System.out.println("└─────────────────────────────────┘");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();  // This calls Person's displayInfo()
        System.out.println("Department: " + department);
        System.out.println("Parcels Delivered: " + parcelsDelivered);
        System.out.println("Performance Rating: " + getPerformanceRating());
    }
}