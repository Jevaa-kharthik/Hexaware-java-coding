package com.example.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CashDonation extends Donation {
    private LocalDate dateOfDonation;

    public CashDonation(String donarName, BigDecimal amount, LocalDateTime dateOfDonation) {
        super(donarName, amount);
        this.dateOfDonation = dateOfDonation.toLocalDate();
    }

    public LocalDate getDateOfDonation() {
        return dateOfDonation;
    }

    public void recordDonation() {
        System.out.println("\nCash donation of " + getDonarAmount() + " received from " + getDonarName() + " on " + dateOfDonation + "\n");
    }

    
}
