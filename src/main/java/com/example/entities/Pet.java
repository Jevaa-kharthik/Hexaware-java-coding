package com.example.entities;

public class Pet {
    private int id;  // Pet ID
    private String name;
    private int age;
    private String breed;

    // Constructor to initialize all attributes
    public Pet(int id, String name, int age, String breed) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.breed = breed;
    }

    // Constructor without id (for when creating new pets)
    public Pet(String name, int age, String breed) {
        this.name = name;
        this.age = age;
        this.breed = breed;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for other fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    // Updated toString() to include the pet's ID
    @Override
    public String toString() {
        return "\nPet Details: " + "\n" +
               "ID = " + id + '\n' +  // Display pet ID
               "Name = " + name + '\n' +
               "Age = " + age + '\n' +
               "Breed = " + breed + '\n';
    }
}