package com.example.entities;

public class Cat extends Pet{
    private String catColor;

    public Cat(int id, String name, int age, String breed, String catColor) {
        super(id, name, age, breed);
        this.catColor = catColor;
    }

    public Cat(int id, String name, int age, String breed) {
        super(id, name, age, breed);
    }

    public Cat(String name, int age, String breed) {
        super(name, age, breed);
    }

    public String getCatColor() {
        return catColor;
    }
    public void setCatColor(String catColor) {
        this.catColor = catColor;
    }
    
}
