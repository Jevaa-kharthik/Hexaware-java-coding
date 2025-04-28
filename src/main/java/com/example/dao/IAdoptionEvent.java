package com.example.dao;

import java.util.List;

import com.example.entities.AdoptionEvent;

public interface IAdoptionEvent {
    boolean hostEvent (AdoptionEvent event);
    boolean registration(int eventID, String participantName);
    List<AdoptionEvent> getAllEvents();
    public boolean adoptPet(int petId);
    
}
