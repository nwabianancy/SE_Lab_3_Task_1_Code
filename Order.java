package de.haw.hamburg.sel.ex_ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;


/**
 * Order class with payment method.
 */
public class Order {
    private int totalCost = 0;
    private boolean isOrderClosed = false;

    private boolean signedInPaypal;
    private boolean signedInCreditCard;


    
    public void processOrder() {

        collectPaypalPaymentDetails();
        collectCreditCardPaymentDetails();

    }

    public void setTotalCost(int cost) {

        this.totalCost += cost;
    }

    public int getTotalCost() {

        return totalCost;
    }

    public boolean isClosed() {

        return isOrderClosed;
    }

    public void setClosed() {

        isOrderClosed = true;
    }
    
    public void collectPaypalPaymentDetails() {
    	BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    	String email;
    	String password;
        
    	try {
            while (!signedInPaypal) {
                System.out.print("Enter the user's email for Paypal: ");
                email = READER.readLine();
                System.out.print("Enter the password: ");
                password = READER.readLine();
                if (verifyPayPalCredentials(email, password)) {
                    System.out.println("Data verification has been successful.");
                } else {
                    System.out.println("Wrong email or password!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean verifyPayPalCredentials(String email, String password) {
        Map<String, String> paypalDatabase = Demo.getPaypalDatabase();
        String storedEmail = paypalDatabase.get(password);
        signedInPaypal = storedEmail != null && storedEmail.equals(email);
        return signedInPaypal;
    }
    public boolean verifyCreditCardCredentials(String cardNumber, String expiryDate, String cvc) {
        // Access the Credit Card database using the Demo getter
        Map<String, String> creditCardDatabase = Demo.getCreditCardDatabase();
        String storedData = creditCardDatabase.get(cardNumber);
        if (storedData != null) {
            String[] parts = storedData.split(",");
            signedInCreditCard = parts[0].equals(expiryDate) && parts[1].equals(cvc);
        } else {
            signedInCreditCard = false;
        }
        return signedInCreditCard;
    }
    public void collectCreditCardPaymentDetails(){
        BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
        String number;
        String date;
        String cvv;
        try{
            while (!signedInCreditCard) {
                System.out.print("Enter the creditcard number: ");
                number = READER.readLine();
                System.out.print("Enter the date: ");
                date = READER.readLine();
                System.out.print("Enter the cvv: ");
                cvv = READER.readLine();
                if (verifyCreditCardCredentials(number, date, cvv)) {
                    System.out.println("Data verification has been successful.");
                } else {
                    System.out.println("Wrong CreditCard Details!");
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    private boolean verifyCreditCardCredentials(String number, String date, String cvv) {
//        setSignedIn(number.equals(CREDITCARD_DATA_BASE.get(number)));
//        return signedInCreditCard;
//    }

    public boolean payWithCreditCard(int totalCost) {
        if (signedInCreditCard) {
            System.out.println("Paying " + totalCost + " using CreditCard.");
            return true;
        } else {
            System.out.println("Payment failed: Not signed in with Credit Card.");
            return false;
        }
    }

//    private void setSignedIn(boolean signedIn) {
//        this.signedInPaypal = signedIn;
//    }


    public boolean payWithPaypal(int totalCost) {
        if (signedInPaypal) {
            System.out.println("Paying " + totalCost + " using PayPal.");
            return true;
        } else {
            System.out.println("Payment failed: Not signed in with PayPal.");
            return false;
        }
    }
}


