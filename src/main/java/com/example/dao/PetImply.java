package com.example.dao;
import com.example.entities.Dog;
import com.example.entities.Pet;
import com.example.util.DBConnUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.entities.Cat;
import java.util.ArrayList;

import java.util.List;

public class PetImply implements IPet {
    Connection connection = DBConnUtil.getConnection("src/main/resources/db.properties");
    public boolean addPet(Pet pet) {
        String query = "INSERT INTO pets (name, age, breed, pet_type) VALUES(?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, pet.getName());
            ps.setInt(2, pet.getAge());
            ps.setString(3, pet.getBreed());
            ps.setString(4, pet instanceof Dog ? "Dog" : "Cat");
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pet added successfully.");
                return true;
            } else {
                System.out.println("Failed to add pet.");
            }
        } catch (SQLException e) {
        }
        return false;
    }

    @Override
    public boolean removePet(int id) {
        String query = "DELETE FROM pets WHERE pet_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pet removed successfully.");
                return true;
            } else {
                System.out.println("Failed to remove pet.");
            }
        } catch (SQLException e) {

        }
        return false;  
    }

    @Override
    public Pet getPet(int id) {
        String query = "SELECT * FROM pets WHERE pet_id = ?";
        Pet pet = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String breed = resultSet.getString("breed");
                String petType = resultSet.getString("pet_type");

                pet = (petType.equals("Dog")) ? new Dog(name, age, breed) : new Cat(name, age, breed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pet;
    }

    @Override
    public List<Pet> getAllPets() {
        String query = "SELECT * FROM pets";
        List<Pet> pets = new ArrayList<>();
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("pet_id");  // Get pet_id from database
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String breed = resultSet.getString("breed");
                String petType = resultSet.getString("pet_type");
    
                // Create the Pet object and set its ID
                Pet pet = (petType.equals("Dog")) ? new Dog(id, name, age, breed) : new Cat(id, name, age, breed);
                pets.add(pet);  // Add pet to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    public int getID(){
        String query = "SELECT pet_id FROM pets";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("pet_id");
                return id;
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public List<Pet> getAllNonAdoptedPets() {
        String query = "SELECT * FROM pets WHERE is_adopted = 0";
        List<Pet> adoptedPets = new ArrayList<>();
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("pet_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String breed = resultSet.getString("breed");
                String petType = resultSet.getString("pet_type");
    
                Pet pet = (petType.equals("Dog")) ? new Dog(id, name, age, breed) : new Cat(id, name, age, breed);
                adoptedPets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adoptedPets;
    }
}
