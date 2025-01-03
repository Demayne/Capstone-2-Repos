# Fast Food Driver Program

# QuickFoodApp

**QuickFoodApp** is a Java-based application designed for managing food delivery orders. It allows users to input customer and restaurant details, select meals from a menu, assign delivery drivers, and generate invoices.

---

## Features

- Input customer and restaurant details.
- View a pizza menu and order multiple items with various sizes.
- Automatically assign the nearest available delivery driver.
- Generate and save detailed invoices for each order.

---

## Setup and Installation

### Prerequisites
- **Java**: Ensure Java 8 or higher is installed on your system.
- **Driver File**: Create a `drivers.txt` file in the program directory with driver details in the following format:

### Steps to Run the Program

1. **Download or Clone the Program**:
 - Place the `QuickFoodApp.java` file and the `drivers.txt` file in the same directory.

2. **Compile the Program**:
 - Open a terminal, navigate to the program directory, and execute:
   ```bash
   javac QuickFoodApp.java
   ```

3. **Run the Program**:
 - Use the following command:
   ```bash
   java QuickFoodApp
   ```

---

## Usage Guide

### Step 1: Input Order Details
- Provide the order number when prompted.
- Enter customer details, including:
- Name, contact number, city, address, email, and any special instructions.

### Step 2: Input Restaurant Information
- Provide the restaurant name, location, and contact number.

### Step 3: Ordering Meals
1. **View Menu**:
 - A menu of pizzas with sizes and prices will be displayed:
   ```
   Pizza Menu:
   1. Margherita (Small: $50, Medium: $70, Large: $90)
   2. Pepperoni (Small: $60, Medium: $80, Large: $100)
   ```

2. **Select Pizzas**:
 - Enter the number for the pizza you want to order.
 - Choose a size by typing `S` (Small), `M` (Medium), or `L` (Large).

3. **Add More Pizzas**:
 - After selecting a pizza, you’ll be asked if you want to add another:
   - Type `y` to add another pizza or `n` to finish.

---

## Invoice Generation

### Delivery Assignment
- The app assigns the nearest driver to the restaurant based on location and load.

### Invoice Creation
- An invoice is generated and saved in a folder called `invoices` within the program directory.
- Example filename: `invoice_101.txt`.

### Console Display
- The invoice details are also printed to the console.

---

## Example Interaction

### Console Input

Enter Order Number: 101 Enter Customer Name: Sarah Johnson Enter Contact Number: 987-654-3210 Enter City: Cape Town Enter Address: 45 Palm Street Enter Email: sarah.johnson@example.com Enter Special Instructions: Extra cheese Enter Restaurant Name: Pizza Haven Enter Restaurant Location: Cape Town Enter Restaurant Contact Number: 021-123-4567

Pizza Menu:

Margherita (Small: $50, Medium: $70, Large: $90)
Pepperoni (Small: $60, Medium: $80, Large: $100)
Enter the number of the pizza you'd like to order: 2 Choose size (S, M, L): L Would you like to add another pizza? (y/n) n

---

## Troubleshooting

- **Invalid Pizza Choice**:
  - If you select an invalid pizza number or size, the program will prompt you to try again.

- **Missing Drivers File**:
  - Ensure the `drivers.txt` file exists in the correct format and location.

- **Errors**:
  - If an error occurs, check the input format and retry.

---

Enjoy using **QuickFoodApp** for your food delivery needs!

