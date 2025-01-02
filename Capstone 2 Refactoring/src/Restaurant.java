public class Restaurant {

    private String name;
    private String location;
    private String contactNumber;  // Contact number for the restaurant

    // Constructor to initialize restaurant details
    public Restaurant(String name, String location, String contactNumber) {
        this.name = name;
        this.location = location;
        this.contactNumber = contactNumber;
    }

    // Getter for Name
    public String getName() {
        return name;
    }

    // Getter for Location
    public String getLocation() {
        return location;
    }

    // Getter for Contact Number
    public String getContactNumber() {
        return contactNumber;
    }
}
