package de.haw.hamburg.sel.ex_ecommerce;

/**
 * Dummy credit card class.
 */
public class CreditCard implements PaymentMethod {
   // private int amount;
    private String cardNumber;
    private String expiryDate;
    private String cvc;

    public CreditCard(String number, String date, String cvv) {
        //this.amount = 100_000;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvc = cvc;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvc() {
        return cvc;
    }

    @Override
    public boolean pay(int amount) {
        System.out.println("Attempting to pay " + amount + " units using Credit Card.");
        return true;
    }

}