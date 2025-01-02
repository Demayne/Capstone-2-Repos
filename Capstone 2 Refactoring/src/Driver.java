public class Driver extends Person {
    private int currentLoad;

    // Constructor for Driver with default contact number
    public Driver(String name, String location, int currentLoad) {
        super(name, "Unknown", location);  // Default contact number as "Unknown"
        this.currentLoad = currentLoad;
    }

    // Constructor for fully initialized Driver object
    public Driver(String name, String contactNumber, String location, int currentLoad) {
        super(name, contactNumber, location);  // Passing attributes to the superclass
        this.currentLoad = currentLoad;
    }

    // Getter for current load
    public int getCurrentLoad() {
        return currentLoad;
    }

    // Setter for current load
    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

    // Method to get the driver's contact number (inherited from Person)
    public String getContactNumber() {
        return super.getContactNumber();
    }

    // Method to check if the driver is available (load < 3)
    public boolean isAvailable() {
        return currentLoad < 3;
    }

    // Method to get the driver information (including inherited person info)
    public String getDriverInfo() {
        return getPersonInfo() + "\nCurrent Load: " + currentLoad;
    }
}
