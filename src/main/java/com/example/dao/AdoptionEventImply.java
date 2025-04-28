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
            e.printStackTrace();
            return false;
        }
    }

    // Register a participant for an event
    @Override
    public boolean registration(int eventID, String participantName) {
        String query = "INSERT INTO participants (event_id, participant_name) VALUES (?, ?)";
        try (Connection connection = DBConnUtil.getConnection("src/main/resources/db.properties");
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, eventID);
            ps.setString(2, participantName);
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
                String eventName = rs.getString("event_name");
                Date eventDate = rs.getDate("event_date");
                AdoptionEvent event = new AdoptionEvent(eventName, eventDate.toLocalDate());
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public boolean adoptPet(int petId) {
        String query = "UPDATE pets SET is_adopted = TRUE WHERE pet_id = ? AND is_adopted = FALSE";  // Only adopt if pet is not already adopted
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
}
