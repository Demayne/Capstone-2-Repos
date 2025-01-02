public abstract class Person {
    // Attributes for the Person class
    private String name;
    private String contactNumber;
    private String location;

    // Constructor to initialize a Person object
    public Person(String name, String contactNumber, String location) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.location = location;
    }

    // Method to get the full information of the person
    public String getPersonInfo() {
        return "Name: " + name +
               "\nContact Number: " + contactNumber +
               "\nLocation: " + location;
    }

    // Getters for Person attributes
    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getLocation() {
        return location;
    }
}
