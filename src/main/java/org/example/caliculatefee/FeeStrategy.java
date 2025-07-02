package org.example.caliculatefee;

public interface FeeStrategy {

    /**
     * Calculates the fee based on the given parameters.
     *
     * @param checkinTime The time when the book was checked in.
     * @param checkoutDuration The duration for which the book was checked out.
     * @return The calculated fee.
     */
    void calculateFee(int checkinTime, int checkoutDuration);
}
