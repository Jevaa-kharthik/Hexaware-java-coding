package com.example.dao;
import com.example.entities.Pet;
import java.util.List;

public interface IPet {
    boolean addPet(Pet pet);
    boolean removePet(Pet pet);
    Pet getPet(int id);
    List<Pet> getAllPets();
    
}
