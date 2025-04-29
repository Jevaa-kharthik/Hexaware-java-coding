package com.example.dao;

import com.example.entities.AdoptionEvent;
import com.example.util.DBConnUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdoptionEventImply implements IAdoptionEvent {

    // Host an event
    @Override
    public boolean hostEvent(AdoptionEvent event) {
        String query = "INSERT INTO adoption_events (event_name, event_date) VALUES (?, ?)";
        try (Connection connection = DBConnUtil.getConnection("src/main/resources/db.properties");
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, event.getEventName());
            ps.setDate(2, java.sql.Date.valueOf(event.getEventDate()));  // Assuming eventDate is a LocalDate
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // Register a participant for an event
    @Override
    public boolean registration(int eventID, int pet_id, String participantName) {
        String query = "INSERT INTO participants (event_id, pet_id, participant_name) VALUES (?, ?, ?)";
        try (Connection connection = DBConnUtil.getConnection("src/main/resources/db.properties");
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, eventID);
            ps.setInt(2, pet_id);
            ps.setString(3, participantName);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all events
    @Override
    public List<AdoptionEvent> getAllEvents() {
        List<AdoptionEvent> events = new ArrayList<>();
        String query = "SELECT * FROM adoption_events";
        try (Connection connection = DBConnUtil.getConnection("src/main/resources/db.properties");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int eventId = rs.getInt("event_id");
                String eventName = rs.getString("event_name");
                Date eventDate = rs.getDate("event_date");
                AdoptionEvent event = new AdoptionEvent(eventId, eventName, eventDate.toLocalDate());
                events.add(event);
            }
        } catch (SQLException e) {
        }
        return events;
    }

    public boolean adoptPet(int petId) {
        String query = "UPDATE pets SET is_adopted = 1 WHERE pet_id = ? AND is_adopted = 0";  // Only adopt if pet is not already adopted
        Connection connection = DBConnUtil.getConnection("src/main/resources/db.properties");
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, petId);
    
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pet adopted successfully.");
                return true;
            } else {
                System.out.println("Pet not available for adoption");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void showAllParticipants() {
        String query = "select p.pet_id, p.name, pa.event_id, pa.participant_name FROM pets p JOIN participants pa ON p.pet_id = pa.pet_id WHERE p.pet_id = pa.pet_id;";
        try (Connection connection = DBConnUtil.getConnection("src/main/resources/db.properties");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int petId = rs.getInt("pet_id");
                String petName = rs.getString("name");
                int eventId = rs.getInt("event_id");
                String participantName = rs.getString("participant_name");
                System.out.println("Event ID: " + eventId + "\nParticipant Name: " + participantName + "\nPet ID: " + petId + "\nPet Name: " + petName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
