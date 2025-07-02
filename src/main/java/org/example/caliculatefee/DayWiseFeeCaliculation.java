package org.example.caliculatefee;

public class DayWiseFeeCaliculation implements FeeStrategy{
    @Override
    public void calculateFee(int checkinTime, int checkoutDuration) {
        double fee=(checkoutDuration - checkinTime) * 1.0;// Assuming a fee of $1 per day
        if(fee<=0){
            System.out.println("Minimum fee is $1.00 for this book");
            return; // No fee to be paid
        }else{
            System.out.println("The fee for the book is: $" + fee);
        }
    }
}
