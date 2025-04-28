package com.example.entities;

public class Dog extends Pet {
    private String dogBreed;

    public Dog(int id, String name, int age, String breed, String dogBreed) {
        super(id, name, age, breed);
        this.dogBreed = dogBreed;
    }

    public Dog(int id, String name, int age, String breed) {
        super(id, name, age, breed);
    }

    public Dog(String name, int age, String breed) {
        super(name, age, breed);
    }
    public String getDogBreed() {
        return dogBreed;
    }
    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    
}
