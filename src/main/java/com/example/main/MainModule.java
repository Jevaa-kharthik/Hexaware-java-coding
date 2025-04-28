package com.example.main;

import com.example.dao.AdoptionEventImply;
import com.example.dao.DonationImply;
import com.example.dao.PetImply;
import com.example.entities.AdoptionEvent;
import com.example.entities.CashDonation;
import com.example.entities.Dog;
import com.example.entities.Cat;
import com.example.entities.Donation;
import com.example.entities.Pet;
import com.example.exception.AdoptionException;
import com.example.exception.InsuffcientFundsException;
import com.example.exception.InvalidPetAgeHandling;
import com.example.exception.NullReferenceException;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MainModule {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Create DAO objects
        PetImply petDao = new PetImply();
        DonationImply donationDao = new DonationImply();
        AdoptionEventImply eventDao = new AdoptionEventImply();

        while (true) {
            System.out.println("\n===== Pet Adoption Platform Menu =====");
            System.out.println("1. Display All Pets");
            System.out.println("2. Record Cash Donation");
            System.out.println("3. Display All Donations");
            System.out.println("4. Display All Events");
            System.out.println("5. Add Pets");
            System.out.println("6. Remove Pets");
            System.out.println("7. Host Event");
            System.out.println("8. Register Participant");
            System.out.println("9. Will you adopt meeeee :) ---> ");
            System.out.println("=======================================\n");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    // Display Pet Listings
                    List<Pet> petListings = petDao.getAllPets();
                    if (petListings.isEmpty()) {
                        System.out.println("No pets available.");
                    } else {
                        for (Pet pet : petListings) {
                            System.out.println(pet);
                        }
                    }
                    break;

                    case 2:
                    // Record Cash Donation
                        System.out.print("Enter donor name: ");
                        String donorName = scanner.nextLine();
                        if(donorName.matches("[a-zA-Z]+") == false){
                            throw new NullReferenceException("Donor name cannot be empty or contain numbers.");
                        }
                        if (donorName == null || donorName.isEmpty()) {
                            throw new NullReferenceException("Donor name cannot be empty.");
                            
                        }
                        System.out.print("Enter donation amount: ");
                        BigDecimal amount = scanner.nextBigDecimal();
                        scanner.nextLine();
                        

                        if (amount.compareTo(BigDecimal.TEN) < 0) { // Less than $10
                            throw new InsuffcientFundsException("Minimum donation amount is $10.");
                        }

                        LocalDateTime donationDateTime = LocalDateTime.now();
                        Donation cashDonation = new CashDonation(donorName, amount, donationDateTime);
                        donationDao.recordDonation(cashDonation);
                        break;
                case 3:
                    // Display All Donations
                    List<Donation> donations = donationDao.getAllDonations();
                    if (donations.isEmpty()) {
                        System.out.println("No donations recorded.");
                    } else {
                        for (Donation donation : donations) {
                            donation.recordDonation();
                        }
                    }
                    break;

                case 4:
                    // Display All Events
                    List<AdoptionEvent> events = eventDao.getAllEvents();
                    if (events.isEmpty()) {
                        System.out.println("No events hosted.");
                    } else {
                        for (AdoptionEvent event : events) {
                            System.out.println(event);
                        }
                    }
                    break;

                case 5:
                    // Add Pets
                    System.out.print("Enter pet name: ");
                    String petName = scanner.nextLine();
                    if(petName.matches("[a-zA-Z]+") == false){
                        throw new NullReferenceException("Pet name cannot be empty or contain numbers.");
                    }
                    if(petName == null || petName.isEmpty()) {
                        throw new NullReferenceException("Pet name cannot be empty.");
                    }
                    System.out.print("Enter pet age: ");
                    int petAge = scanner.nextInt();
                    if (petAge < 0) {
                        throw new InvalidPetAgeHandling("Pet age cannot be negative.");
                    }
                    if (petAge > 20) {
                        throw new InvalidPetAgeHandling("Pet age cannot exceed 20 years.");
                    }
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter pet breed: ");
                    String petBreed = scanner.nextLine();
                    if(petBreed.matches("[a-zA-Z]+") == false){
                        throw new NullReferenceException("Pet breed cannot be empty or contain numbers.");
                    }
                    if (petBreed == null || petBreed.isEmpty()) {
                        throw new NullReferenceException("Pet breed cannot be empty.");
                    }
                    System.out.print("Enter pet type (1 for Dog, 2 for Cat): ");
                    int petType = scanner.nextInt();
                    scanner.nextLine();

                    Pet pet;
                    if (petType == 1) {
                        pet = new Dog(petName, petAge, petBreed);
                    } else if (petType == 2) {
                        pet = new Cat(petName, petAge, petBreed);
                    } else {
                        System.out.println("Invalid pet type selected.");
                        break;
                    }
                    petDao.addPet(pet);
                    break;

                case 6:
                    // Remove Pets
                    System.out.print("Enter pet ID to remove: ");
                    int petId = scanner.nextInt();
                    if(petId <= 0) {
                        throw new NullReferenceException("Pet ID cannot be negative.");
                    }
                    scanner.nextLine(); // consume newline
                    Pet petToRemove = petDao.getPet(petId);
                    if (petToRemove != null) {
                        petDao.removePet(petToRemove);
                    } else {
                        System.out.println("Pet not found.");
                    }
                    break;

                case 7:
                    // Host Event
                    System.out.print("Enter event name: ");
                    String eventName = scanner.nextLine();
                    if(eventName.matches("[a-zA-Z0-9]+") == false){
                        throw new NullReferenceException("Event name cannot be empty or contain numbers.");
                    }
                    if (eventName == null || eventName.isEmpty()) {
                        throw new NullReferenceException("Event name cannot be empty.");
                        
                    }
                    System.out.print("Enter event date (YYYY-MM-DD): ");
                    String eventDateStr = scanner.nextLine();
                    if(eventDateStr.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}") == false){
                        throw new NullReferenceException("Event date cannot be empty or contain numbers.");
                    }
                    LocalDate eventDate = LocalDate.parse(eventDateStr);

                    AdoptionEvent adoptionEvent = new AdoptionEvent(eventName, eventDate);
                    boolean hosted = eventDao.hostEvent(adoptionEvent);
                    if (hosted) {
                        System.out.println("Event hosted successfully.");
                    } else {
                        System.out.println("Failed to host event.");
                    }
                    break;
                case 8:
                    System.out.print("Enter Event ID to register in: ");
                    int eventId = scanner.nextInt();
                    if(eventId <= 0) {
                        throw new NullReferenceException("Event ID cannot be negative.");
                    }
                    scanner.nextLine();
                    System.out.print("Enter participant name (pet name): ");
                    String participantName = scanner.nextLine();
                    if(participantName.matches("[a-zA-Z]+") == false){
                        throw new NullReferenceException("Participant name cannot be empty or contain numbers.");
                    }

                    if (participantName == null || participantName.isEmpty()) {
                        throw new AdoptionException("Participant name cannot be empty.");
                    }

                    boolean registered = eventDao.registration(eventId, participantName);
                    if (registered) {
                        System.out.println("Participant registered successfully.");
                    } else {
                        System.out.println("Failed to register participant.");
                    }
                    break;
                    case 9:
                    // Adopt Pet
                    System.out.print("Enter pet ID to adopt: ");
                    int pet_id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                
                    if (pet_id <= 0) {
                        System.out.println("Invalid pet ID.");
                    } else {
                        boolean adopted = eventDao.adoptPet(pet_id);  // Call adoptPet() to mark the pet as adopted
                        if (adopted) {
                            System.out.println("I'm Grateful for Adopting me.");
                        } else if(!adopted) {
                            System.out.println("Pet is already adopted");
                        }
                    }
                    break;
                case 10:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}