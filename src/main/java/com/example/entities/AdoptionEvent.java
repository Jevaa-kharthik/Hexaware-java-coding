package com.example.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdoptionEvent {
    private String eventName;
    private LocalDate eventDate;
    private List<IAdoptable> participants = new ArrayList<>();

    // Constructor to initialize event with name and date
    public AdoptionEvent(String eventName, LocalDate eventDate) {
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    // Getter for eventName
    public String getEventName() {
        return eventName;
    }

    // Setter for eventName
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    // Getter for eventDate
    public LocalDate getEventDate() {
        return eventDate;
    }

    // Setter for eventDate
    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    // Register a participant (who must implement IAdoptable)
    public boolean registerParticipant(IAdoptable participant) {
        return participants.add(participant);
    }

    // Return all registered participants
    public List<IAdoptable> getParticipants() {
        return new ArrayList<>(participants);  // Returning a copy to avoid external modifications
    }

    // Host the event and get the participants
    public List<IAdoptable> hostEvent() {
        // You could add more logic here to manage the event
        return getParticipants();
    }

    public String toString() {
        return "AdoptionEvent " + "\n" + 
                "Event Name='" + eventName + '\n' +
                "Event Date=" + eventDate + '\n';
    }
}