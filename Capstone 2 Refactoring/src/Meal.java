public class Meal {

    private String name;
    private double price;
    private int quantity;

    // Constructor to initialize meal details
    public Meal(String name, double price, int quantity) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter for Name
    public String getName() {
        return name;
    }

    // Getter for Price
    public double getPrice() {
        return price;
    }

    // Getter for Quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter for Price
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    // Setter for Quantity
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    // Increase quantity
    public void increaseQuantity(int additionalQuantity) {
        if (additionalQuantity < 0) {
            throw new IllegalArgumentException("Additional quantity cannot be negative.");
        }
        this.quantity += additionalQuantity;
    }

    // Calculate discounted price
    public double calculateDiscountedPrice(double discountRate) {
        if (discountRate < 0 || discountRate > 1) {
            throw new IllegalArgumentException("Discount rate must be between 0 and 1.");
        }
        return price * (1 - discountRate);
    }

    // Override toString
    @Override
    public String toString() {
        return quantity + " x " + name + " @ R" + price + " each";
    }
}
