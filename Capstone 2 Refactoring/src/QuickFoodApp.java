import java.util.*;

/**
 * QuickFoodApp: A simple application to manage food orders.
 * This application collects customer details, restaurant details, meal selections,
 * and generates an invoice for the order. It also assigns the nearest driver for delivery.
 */
public class QuickFoodApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Get order number before anything else
            int orderNumber = getOrderNumber(scanner);
            
            // Collect customer details
            Customer customer = getCustomerDetails(scanner);

            // Collect restaurant details
            Restaurant restaurant = getRestaurantDetails(scanner);

            // Select meals
            HashMap<String, Integer> meals = new HashMap<>();
            HashMap<String, Double> mealPrices = new HashMap<>();
            selectMeals(scanner, meals, mealPrices);

            // Collect order details
            Order order = new Order(orderNumber, customer, restaurant, meals, mealPrices);

            // Handle delivery
            DeliverySystem deliverySystem = new DeliverySystem("drivers.txt");
            Driver nearestDriver = deliverySystem.findNearestDriver(restaurant.getLocation(), customer.getCity());

            // Generate and display invoice
            deliverySystem.generateInvoice(order, nearestDriver);
            System.out.println("Invoice generated successfully!");

        } catch (NumberFormatException e) {
            // Handle invalid number input
            System.out.println("Invalid input. Please enter numbers where required.");
        } catch (InputMismatchException e) {
            // Handle incorrect input format
            System.out.println("Error in input format: " + e.getMessage());
        } catch (Exception e) {
            // Handle any unexpected errors
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            // Ensure the scanner is closed to prevent resource leaks
            scanner.close();
        }
    }

    /**
     * Receives the customer's order number before proceeding with the rest of the process.
     * @param scanner Scanner object for reading user input
     * @return The order number entered by the customer
     */
    private static int getOrderNumber(Scanner scanner) {
        System.out.println("Enter Order Number:");
        while (true) {
            try {
                int orderNumber = Integer.parseInt(scanner.nextLine()); // Capture order number
                return orderNumber;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid order number.");
            }
        }
    }

    /**
     * Collects customer details through user input.
     * @param scanner Scanner object for reading user input
     * @return Customer object containing the inputed details
     */
    private static Customer getCustomerDetails(Scanner scanner) {
        String name = getInput(scanner, "Enter Customer Name:");
        String contactNumber = getInput(scanner, "Enter Contact Number:");
        while (contactNumber.isEmpty()) {
            System.out.println("Contact Number cannot be null or empty.");
            contactNumber = getInput(scanner, "Enter Contact Number:");
        }
        
        String city = getInput(scanner, "Enter City:");
        String address = getInput(scanner, "Enter Address:");
        String email = getInput(scanner, "Enter Email:");
        String specialInstructions = getInput(scanner, "Enter Special Instructions:");
        
        return new Customer(name, contactNumber, city, address, email, specialInstructions);
    }

    /**
     * Helper method to get input for required fields with validation.
     * @param scanner Scanner object for reading user input
     * @param prompt The prompt message to display to the user
     * @return The valid input entered by the user
     */
    private static String getInput(Scanner scanner, String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        while (input.isEmpty()) {
            System.out.println("This field cannot be empty. Please try again.");
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Collects restaurant details through user input.
     * @param scanner Scanner object for reading user input
     * @return Restaurant object containing the inputted details
     */
    private static Restaurant getRestaurantDetails(Scanner scanner) {
        String restaurantName = getInput(scanner, "Enter Fast Food Restaurant Name:");
        String restaurantLocation = getInput(scanner, "Enter Fast Food Restaurant Location:");
        String restaurantContact = getInput(scanner, "Enter Fast Food Restaurant Contact Number:");

        return new Restaurant(restaurantName, restaurantLocation, restaurantContact);
    }

    /**
     * Allows the user to select meals from a predefined menu and store their choices.
     * @param scanner Scanner object for reading user input
     * @param meals HashMap to store meal names and their quantities
     * @param mealPrices HashMap to store meal names and their prices
     */
    private static void selectMeals(Scanner scanner, HashMap<String, Integer> meals, HashMap<String, Double> mealPrices) {
        boolean addMore = true;

        while (addMore) {
            System.out.println("\nFast Food Menu:");
            System.out.println("1. Cheeseburger (S: $50, M: $70, L: $90)");
            System.out.println("2. Chicken Wrap (S: $60, M: $80, L: $100)");
            System.out.println("3. Veggie Burger (S: $65, M: $85, L: $105)");
            System.out.println("4. Chicken Nuggets (S: $70, M: $90, L: $110)");
            System.out.println("5. French Fries (S: $55, M: $75, L: $95)");

            try {
                System.out.println("\nEnter the number of the fast food item you'd like to order:");
                int foodChoice = Integer.parseInt(scanner.nextLine());

                System.out.println("Choose size (S, M, L):");
                String size = scanner.nextLine().toUpperCase();

                if (!size.matches("[SML]")) {
                    // Validate size input
                    System.out.println("Invalid input. Please enter S, M, or L.");
                    continue;
                }

                String foodName;
                double foodPrice;

                switch (foodChoice) {
                    case 1:
                        foodName = "Cheeseburger";
                        foodPrice = calculatePrice(size, 50, 70, 90);
                        break;
                    case 2:
                        foodName = "Chicken Wrap";
                        foodPrice = calculatePrice(size, 60, 80, 100);
                        break;
                    case 3:
                        foodName = "Veggie Burger";
                        foodPrice = calculatePrice(size, 65, 85, 105);
                        break;
                    case 4:
                        foodName = "Chicken Nuggets";
                        foodPrice = calculatePrice(size, 70, 90, 110);
                        break;
                    case 5:
                        foodName = "French Fries";
                        foodPrice = calculatePrice(size, 55, 75, 95);
                        break;
                    default:
                        // Handle invalid menu choice
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

                // Update meal selections
                meals.put(foodName, meals.getOrDefault(foodName, 0) + 1);
                mealPrices.put(foodName, foodPrice);

                System.out.println("Would you like to add another item? (y/n)");
                addMore = scanner.nextLine().equalsIgnoreCase("y");

            } catch (NumberFormatException e) {
                // Handle invalid input for food choice
                System.out.println("Invalid input. Please enter a valid number for the food choice.");
            }
        }
    }

    /**
     * Calculates the price of a meal based on the selected size.
     * @param size Selected size (S, M, L)
     * @param small Price for small size
     * @param medium Price for medium size
     * @param large Price for large size
     * @return Price of the selected meal size
     */
    private static double calculatePrice(String size, double small, double medium, double large) {
        return size.equals("S") ? small : size.equals("M") ? medium : large;
    }
}
