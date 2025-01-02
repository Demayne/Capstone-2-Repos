import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private String name;
    private String contactNumber;
    private String location;  // Combines city and address
    private String email;
    private String specialInstructions;

    // Constructor for five parameters
    public Customer(String name, String contactNumber, String location, String email, String specialInstructions) {
        this.name = validateString(name, "Name");
        this.contactNumber = validateString(contactNumber, "Contact Number");
        this.location = validateString(location, "Location");
        this.email = validateEmail(email);
        this.specialInstructions = specialInstructions == null ? "" : specialInstructions;  // Optional field
    }

    // Constructor for six parameters (splitting city and address)
    public Customer(String name, String contactNumber, String city, String address, String email, String specialInstructions) {
        this.name = validateString(name, "Name");
        this.contactNumber = validateString(contactNumber, "Contact Number");
        this.location = validateString(city, "City") + ", " + validateString(address, "Address");
        this.email = validateEmail(email);
        this.specialInstructions = specialInstructions == null ? "" : specialInstructions;  // Optional field
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validateString(name, "Name");
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = validateString(contactNumber, "Contact Number");
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = validateString(location, "Location");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = validateEmail(email);
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions == null ? "" : specialInstructions;
    }

    // Optional: A method to retrieve city and address separately
    public String getCity() {
        if (location.contains(",")) {
            return location.split(",")[0].trim();
        }
        return location;
    }

    public String getAddress() {
        if (location.contains(",")) {
            return location.split(",")[1].trim();
        }
        return "";
    }

    // Optional: A method to set city and address separately
    public void setCity(String city) {
        String address = getAddress();  // Retains the existing address
        this.location = city + ", " + address;
    }

    public void setAddress(String address) {
        String city = getCity();  // Retains the existing city
        this.location = city + ", " + address;
    }

    // Validate that a string is not null or empty, and throw an exception if invalid
    private String validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
        }
        return value.trim();
    }

    // Validate email format using a regular expression
    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        // Simple email validation pattern
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = emailPattern.matcher(email.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        return email.trim();
    }
}
