import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.*;
import java.util.stream.*;


/**
 * DeliverySystem class handles the main logic of order processing, driver assignment,
 * invoice generation, and logging in the food delivery system.
 */
public class DeliverySystem {
    private List<Driver> drivers = Collections.synchronizedList(new ArrayList<>()); // Thread-safe driver list
    private static final Logger logger = Logger.getLogger(DeliverySystem.class.getName());
    private List<String> invalidLines = new ArrayList<>(); // Track invalid lines for reporting

    /**
     * Constructor to initialize the delivery system and load drivers from a file.
     * 
     * @param driversFile The name of the file containing driver data.
     */
    public DeliverySystem(String driversFile) {
        loadDrivers(driversFile);
    }

    /**
     * Loads the driver data from the specified file and initializes driver objects.
     * 
     * @param driversFile The file path containing driver data.
     */
    private void loadDrivers(String driversFile) {
        Path path = Paths.get("resources", driversFile);
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(this::parseDriverLine);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading drivers file: " + e.getMessage(), e);
        }
        if (!invalidLines.isEmpty()) {
            logger.log(Level.WARNING, "The following lines were skipped due to invalid format: " + invalidLines);
        }
    }

    /**
     * Parses a single line of the driver file and adds it to the list of drivers.
     * 
     * @param line The line to parse.
     */
    private void parseDriverLine(String line) {
        String[] parts = line.split(", ");
        if (parts.length < 3) {
            invalidLines.add(line);
            logger.log(Level.WARNING, "Skipped invalid driver line (insufficient fields): " + line);
            return; // Skip malformed lines
        }

        String name = capitalizeFirstLetter(parts[0]);
        String location = capitalizeFirstLetter(parts[1]);
        int load = parseLoad(parts[2]);

        if (load < 0) {
            invalidLines.add(line);
            logger.log(Level.WARNING, "Skipped invalid driver line (invalid load): " + line);
            return; // Skip invalid loads
        }

        drivers.add(new Driver(name, location, load));
    }

    /**
     * Parses the load (number of orders) and returns an integer.
     * 
     * @param loadStr The load in string format.
     * @return The parsed load as an integer.
     */
    private int parseLoad(String loadStr) {
        try {
            return Integer.parseInt(loadStr);
        } catch (NumberFormatException e) {
            return -1; // Return an invalid load in case of parsing failure
        }
    }

    /**
     * Finds the nearest available driver for delivery.
     * 
     * @param restaurantLocation The location of the restaurant.
     * @param customerLocation   The location of the customer.
     * @return The nearest available driver.
     */
    public Driver findNearestDriver(String restaurantLocation, String customerLocation) {
        return drivers.stream()
                .filter(driver -> driver.getLocation().equalsIgnoreCase(restaurantLocation)
                        || driver.getLocation().equalsIgnoreCase(customerLocation))
                .min(Comparator.comparingInt(Driver::getCurrentLoad))
                .orElse(null); // Return null if no available driver found
    }

    /**
     * Generates an invoice for the order and writes it to a file and console.
     * 
     * @param order      The order object containing details for invoice generation.
     * @param driver     The nearest driver assigned to the order.
     */
    public void generateInvoice(Order order, Driver nearestDriver) {
        // Print invoice to file
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("invoices", "order_" + order.getOrderNumber() + ".txt"))) {
            writeInvoiceHeader(writer, order);
            writeOrderDetails(writer, order);
            writeDriverDetails(writer, nearestDriver, order); // Pass 'order' here
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing invoice to file: " + e.getMessage(), e);
        }

        // Print invoice to console
        printInvoiceToConsole(order, nearestDriver);
    }


    // Helper methods to write different sections of the invoice
    private void writeInvoiceHeader(BufferedWriter writer, Order order) throws IOException {
        writer.write("Order Number: " + order.getOrderNumber());
        writer.newLine();  // Move to the next line before printing the dashed line
        writer.write("-------------------------------------------------------------------------------");
        writer.newLine();
        writer.write("Customer: " + order.getCustomer().getName());
        writer.newLine();
        writer.write("Email: " + order.getCustomer().getEmail());
        writer.newLine();
        writer.write("Phone: " + order.getCustomer().getContactNumber());
        writer.newLine();
        writer.write("City: " + order.getCustomer().getCity());
        writer.newLine();
        writer.write("-------------------------------------------------------------------------------");
        writer.newLine();
        writer.write("Meals Ordered:");
        writer.newLine();
    }

    private void writeOrderDetails(BufferedWriter writer, Order order) throws IOException {
        for (Map.Entry<String, Integer> meal : order.getMeals().entrySet()) {
            String mealName = capitalizeFirstLetter(meal.getKey());
            int quantity = meal.getValue();
            double price = order.getMealPrices().get(meal.getKey());
            writer.write(String.format("%d x %-20s @ $%.2f\n", quantity, mealName, price));
        }
        writer.write("-------------------------------------------------------------------------------");
        writer.newLine();
        writer.write("Total: $");
        writer.write(String.format("%.2f", order.calculateTotalCost()));
        writer.newLine();
    }

    private void writeDriverDetails(BufferedWriter writer, Driver nearestDriver, Order order) throws IOException {
        if (nearestDriver != null) {
            writer.write( nearestDriver.getName() + " will be delivering your food. ");
            writer.newLine();
            writer.write("Driver Location: " + nearestDriver.getLocation());
            writer.newLine();
            writer.write("If you would like to contact the restaurant, call: " + order.getRestaurant().getContactNumber());
            writer.newLine();
        } else {
            writer.write("No drivers available at the moment.");
            writer.newLine();
        }
        writer.write("-------------------------------------------------------------------------------");
    }


    private void printInvoiceToConsole(Order order, Driver nearestDriver) {
        // Print the invoice details exactly as in the invoice file format
        System.out.println("Order Number: " + order.getOrderNumber());
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Customer: " + order.getCustomer().getName());
        System.out.println("Email: " + order.getCustomer().getEmail());
        System.out.println("Phone: " + order.getCustomer().getContactNumber());
        System.out.println("City: " + order.getCustomer().getCity());
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Meals Ordered:");

        // Print each meal with its details
        for (Map.Entry<String, Integer> meal : order.getMeals().entrySet()) {
            String mealName = capitalizeFirstLetter(meal.getKey());
            int quantity = meal.getValue();
            double price = order.getMealPrices().get(meal.getKey());
            System.out.printf("%d x %-20s @ $%.2f\n", quantity, mealName, price);
        }

        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Total: $" + String.format("%.2f", order.calculateTotalCost()));
        System.out.println("-------------------------------------------------------------------------------");

        // Print driver details, or note if no drivers are available
        if (nearestDriver != null) {
            System.out.println( nearestDriver.getName() + " will be delivering your food. ");
            System.out.println("Driver Location: " + nearestDriver.getLocation());
            System.out.println("If you would like to contact the restaurant, call: " + order.getRestaurant().getContactNumber());
        } else {
            System.out.println("No drivers available at the moment.");
        }
        System.out.println("-------------------------------------------------------------------------------");
    }


    // Capitalize first letter for formatting consistency
    private String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
