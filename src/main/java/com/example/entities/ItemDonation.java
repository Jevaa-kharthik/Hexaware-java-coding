package com.example.entities;

import java.math.BigDecimal;

public class ItemDonation extends Donation {
    private String itemType;

    public ItemDonation(String donarName, BigDecimal donarAmount, String itemType) {
        super(donarName, donarAmount);
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void recordDonation() {
        System.out.println("Item donation of " + getDonarAmount() + " received from " + getDonarName() + " for item type: " + itemType);
    }
}