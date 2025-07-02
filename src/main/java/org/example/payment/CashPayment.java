package org.example.payment;

public class CashPayment implements PaymentChannel {

    @Override
    public void pay() {
        System.out.println("Payment made using cash.");
    }

    // Additional methods specific to cash payment can be added here
}
