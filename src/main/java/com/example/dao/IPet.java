package com.example.dao;
import com.example.entities.Pet;
import java.util.List;

public interface IPet {
    boolean addPet(Pet pet);
    boolean removePet(int id);
    Pet getPet(int id);
    List<Pet> getAllPets();
    List<Pet> getAllNonAdoptedPets();
    
}
