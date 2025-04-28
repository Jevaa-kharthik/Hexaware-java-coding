package com.example.entities;

import java.math.BigDecimal;

public abstract class Donation {
    private String donarName;
    private BigDecimal donarAmount;

    public Donation(String donarName, BigDecimal donarAmount) {
        this.donarName = donarName;
        this.donarAmount = donarAmount;
    }

    public abstract void recordDonation();


    public String getDonarName() {
        return donarName;
    }
    public BigDecimal getDonarAmount() {
        return donarAmount;
    }
}
