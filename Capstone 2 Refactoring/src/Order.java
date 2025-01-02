import java.util.HashMap;

public class Order {

    private int orderNumber;
    private Restaurant restaurant;
    private Customer customer;
    private HashMap<String, Integer> meals;  // Meal names and quantities
    private HashMap<String, Double> mealPrices;  // Meal names and prices
    private String specialInstructions;

    // Constructor to initialize the order
    public Order(int orderNumber, Customer customer, Restaurant restaurant, HashMap<String, Integer> meals, HashMap<String, Double> mealPrices) {
        this.orderNumber = orderNumber;
        this.restaurant = restaurant;
        this.customer = customer;
        this.meals = meals;
        this.mealPrices = mealPrices;
        this.specialInstructions = customer.getSpecialInstructions();  // Assuming customer may have special instructions
    }

    // Getters and Setters
    public int getOrderNumber() {
        return orderNumber;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Customer getCustomer() {
        return customer;
    }

    public HashMap<String, Integer> getMeals() {
        return meals;
    }

    public HashMap<String, Double> getMealPrices() {
        return mealPrices;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    // Method to calculate the total cost of the order
    public double calculateTotalCost() {
        double total = 0;
        for (String meal : meals.keySet()) {
            int quantity = meals.get(meal);
            double price = mealPrices.get(meal);
            total += quantity * price;
        }
        return total;
    }

    // Print Invoice - Returning invoice as a string with formatting for better readability
    public String printInvoice() {
        StringBuilder invoice = new StringBuilder();
        
        invoice.append("Invoice for Order Number: ").append(orderNumber).append("\n");
        invoice.append("Customer: ").append(customer.getName()).append("\n");
        invoice.append("Email: ").append(customer.getEmail()).append("\n");
        invoice.append("Phone: ").append(customer.getContactNumber()).append("\n");
        invoice.append("City: ").append(customer.getCity()).append("\n");
        invoice.append("Restaurant: ").append(restaurant.getName()).append("\n");
        invoice.append("Restaurant Location: ").append(restaurant.getLocation()).append("\n");
        invoice.append("Meals Ordered:\n");

        double total = 0;
        for (String meal : meals.keySet()) {
            int quantity = meals.get(meal);
            double price = mealPrices.get(meal);
            invoice.append(String.format("%-3d x %-20s @ $%.2f\n", quantity, meal, price));
            total += quantity * price;
        }

        invoice.append("Special Instructions: ");
        if (specialInstructions == null || specialInstructions.isEmpty()) {
            invoice.append("None");
        } else {
            invoice.append(specialInstructions);
        }
        invoice.append("\nTotal: $").append(String.format("%.2f", total)).append("\n");

        return invoice.toString();
    }

    // Optional method to update special instructions
    public void setSpecialInstructions(String instructions) {
        this.specialInstructions = instructions;
    }

    // Optional method to update meals
    public void addMeal(String mealName, int quantity) {
        if (meals.containsKey(mealName)) {
            meals.put(mealName, meals.get(mealName) + quantity);  // Update quantity if meal exists
        } else {
            meals.put(mealName, quantity);  // Add new meal if it doesn't exist
        }
    }

    // Optional method to update meal prices
    public void updateMealPrice(String mealName, double price) {
        if (price > 0) {
            mealPrices.put(mealName, price);  // Only update if the price is valid
        } else {
            System.out.println("Invalid price. Cannot update.");
        }
    }
}
