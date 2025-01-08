package de.haw.hamburg.sel.ex_ecommerce;

import java.util.HashMap;
import java.util.Map;

public class PayPal implements PaymentMethod{
    private String email;
    private String password;

    public PayPal(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    @Override
        public boolean pay(int amount) {
            System.out.println("Attempting to pay " + amount + " units using Credit Card.");
            return true;
        }


}
