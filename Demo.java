package de.haw.hamburg.sel.ex_ecommerce;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import de.haw.hamburg.sel.ex_ecommerce.Order;

/**
 * World first console e-commerce application.
 */
public class Demo {

    private static Map<Integer, Integer> priceOnProducts = new HashMap<>();
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Order order = new Order();


    static {
        priceOnProducts.put(1, 2200);
        priceOnProducts.put(2, 1850);
        priceOnProducts.put(3, 1100);
        priceOnProducts.put(4, 890);
    }

    public static Map<String, String> getCreditCardDatabase() {
        return CREDITCARD_DATABASE;
    }

    private static final Map<String, String> CREDITCARD_DATABASE= new HashMap<>();
    static {
        CREDITCARD_DATABASE.put("1234567812345678", "12/26,123");
        CREDITCARD_DATABASE.put("8765432187654321", "01/25,456");
        CREDITCARD_DATABASE.put("1111222233334444", "05/24,789");
        CREDITCARD_DATABASE.put("5555666677778888","10/27,321");
        CREDITCARD_DATABASE.put("5555666677778856", "10/26,391");

    }

    // return parts[0].equals(expiryDate) && parts[1].equals(cvc)
    private static final Map<String, String> PAYPAL_DATABASE= new HashMap<>();
    static {
        PAYPAL_DATABASE.put("user1@shoppingcarte.de", "pass123");
        PAYPAL_DATABASE.put("user2@shoppingcarte.de", "secure456");
        PAYPAL_DATABASE.put("trainerseen@shoppingcarte.de", "admin789");
        PAYPAL_DATABASE.put("guest111", "guest@shoppingcarte.de");
        PAYPAL_DATABASE.put("shopper222", "shopper@shoppingcarte.de");

    }
    public static Map<String, String> getPaypalDatabase() {
        return PAYPAL_DATABASE;
    }
    public static void main(String[] args) throws IOException {
        while (!order.isClosed()) {
            System.out.println("PayPal Database: " + PAYPAL_DATABASE);

            selectProducts();
            choosePaymentMethod();
        }
        System.out.println("Thank you for shopping with us!");
    }



    //int cost;
    private static void selectProducts() throws IOException {

        String continueChoice = "";
        do {
            System.out.print("Please, select a product:" + "\n" +
                    "1 - Mother board" + "\n" +
                    "2 - CPU" + "\n" +
                    "3 - HDD" + "\n" +
                    "4 - Memory" + "\n");

            int choice = Integer.parseInt(reader.readLine());

            if (!priceOnProducts.containsKey(choice)) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }
            int cost = priceOnProducts.get(choice);
            System.out.print("Count: ");
            int count = Integer.parseInt(reader.readLine());

            order.setTotalCost(cost * count);

            System.out.print("Do you wish to continue selecting products? Y/N: ");
            continueChoice = reader.readLine();

        } while (continueChoice.equalsIgnoreCase("Y"));
    }


    // Order object gathers payment data
    private static void choosePaymentMethod() throws IOException {
        System.out.println("Total cost: " + order.getTotalCost() + " units.");

        // Display the available payment methods
        System.out.println("Choose a payment method:");
        System.out.println("1 - PayPal\n2 - Credit Card");

        int method = Integer.parseInt(reader.readLine());
        PaymentMethod paymentMethod = null;

        if (method == 1) {

            System.out.print("Enter PayPal email: ");
            String email = reader.readLine();

            System.out.print("Enter PayPal password: ");
            String password = reader.readLine();

            // Validate PayPal credentials before creating the object
            if (!validatePaypalCredentials(email, password)) {
                System.out.println("Invalid PayPal credentials. Please try again.");
                return;

            }

            // Handle payment with Paypal
            if (order.payWithPaypal(order.getTotalCost())) {
                System.out.println("Payment has been successful.");
            } else {
                System.out.println("FAIL! Please, check your data.");
            }
            order.setClosed();
        }
        String cardNumber;
        if (method == 2) {
            System.out.print("Enter Card number: ");
            cardNumber = reader.readLine();

            System.out.print("Enter Card expiry: ");
            String expiryDate = reader.readLine();

            System.out.print("Enter Card cvc: ");
            String cvc = reader.readLine();

            //check input
            if (!checkifCreditCardDetailarecomplete(cardNumber, expiryDate, cvc)) {
                System.out.println("Error: Incorrect creditCard details, please try again.");
                return;
            }
            if (!validateCreditCardDetails(cardNumber, expiryDate, cvc)) {
                System.out.println("Error: Invalid CreditCard details.");
                return;
            }
            order.processOrder();

            System.out.print("Pay " + order.getTotalCost() + " units or Continue shopping? P/C: ");
            String proceed = reader.readLine();

            System.out.print("Thank you. Goodbye!");
        }


    }

    private static boolean validatePaypalCredentials(String email, String password) {
        System.out.println("Validating email: " + email + ", password: " + password);
        String storedPassword = PAYPAL_DATABASE.get(email);
        System.out.println("Stored password: " + storedPassword);
        return storedPassword != null && storedPassword.equals(password);

    }

    private static boolean checkifCreditCardDetailarecomplete(String cardNumber, String expiryDate, String cvc) {
        return cardNumber.length()== 16 && expiryDate.matches("\\d{2}/\\d{2}") && cvc.length() == 3;
    }

    private static boolean validateCreditCardDetails(String cardNumber, String expiryDate, String cvc) {
        String databaseValue = CREDITCARD_DATABASE.get(cardNumber);
        if (databaseValue != null) {
            String[] parts = databaseValue.split(",");
            return parts[0].equals(expiryDate) && parts[1].equals(cvc);
        }
        return false;
    }
}


