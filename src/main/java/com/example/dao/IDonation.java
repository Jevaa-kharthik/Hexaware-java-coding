package com.example.dao;
import com.example.entities.Donation;
import java.util.List;

public interface IDonation {
    List<Donation> getAllDonations();
    void recordDonation(Donation donation);
    
}