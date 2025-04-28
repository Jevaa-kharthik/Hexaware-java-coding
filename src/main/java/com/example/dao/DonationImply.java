package com.example.dao;

import java.util.List;
import java.math.BigDecimal;
import java.sql.Connection;

import com.example.entities.CashDonation;
import com.example.entities.Donation;
import com.example.entities.ItemDonation;
import com.example.util.DBConnUtil;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class DonationImply implements IDonation{

    Connection connection = DBConnUtil.getConnection("src/main/resources/db.properties");

    public List<Donation> getAllDonations() {
        List<Donation> donations = new ArrayList<>();
        String query = "SELECT * FROM donations";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String donorName = rs.getString("donor_name");
                BigDecimal amount = rs.getBigDecimal("amount");
                String donationType = rs.getString("donation_type");
                Timestamp donationDate = rs.getTimestamp("donation_date");

                Donation donation = donationType.equals("Cash") ?
                        new CashDonation(donorName, amount, donationDate.toLocalDateTime()) :
                        new ItemDonation(donorName, amount, rs.getString("item_type"));

                donations.add(donation);
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error: Failed to retrieve donations. " + e.getMessage());
            e.printStackTrace();
        }
        return donations;
        
    }

@Override
    public void recordDonation(Donation donation) {
        String query = "INSERT INTO donations (donor_name, amount, donation_type, donation_date) VALUES (?, ?, ?, CURDATE())";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            if (donation.getDonarAmount().compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Donation amount must be greater than zero.");
                return;
            }
            ps.setString(1, donation.getDonarName());
            ps.setBigDecimal(2, donation.getDonarAmount());  // Corrected to use double instead of int
            // Handle donation type based on class
            ps.setString(3, donation instanceof com.example.entities.CashDonation ? "Cash" : "Item");
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Donation recorded successfully.");
            } else {
                System.out.println("Failed to record donation.");
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error: Failed to record donation. " + e.getMessage());
            e.printStackTrace();
        }
    }
}
