package com.example.entities;

import java.util.ArrayList;
import java.util.List;

public class PetShleter{

    private List<Pet> pets = new ArrayList<>();
    public void addPet(Pet pet) {
        pets.add(pet);
    }
    public void removePet(Pet pet) {
        pets.remove(pet);
    }
    public List<Pet> getPets() {
        return new ArrayList<>(pets);
    }
    
}