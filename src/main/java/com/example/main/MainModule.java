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
            System.out.println("2. Display All Donations");
            System.out.println("3. Display All Events");
            System.out.println("4. Record Cash Donation");
            System.out.println("5. Add Pets");
            System.out.println("6. Remove Pets");
            System.out.println("7. Host Event");
            System.out.println("8. Register Participant");
            System.out.println("9. Show All Participants");
            System.out.println("10. Available for Adoption");
            System.out.println("11. Will you adopt meeeee :) ---> ");
            System.out.println("12. All the adopted pets");
            System.out.println("13. Exit");
            System.out.println("=======================================\n");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
                continue;
            }
            scanner.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
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
                        List<Donation> donations = donationDao.getAllDonations();
                        if (donations.isEmpty()) {
                            System.out.println("No donations recorded.");
                        } else {
                            for (Donation donation : donations) {
                                donation.recordDonation();
                            }
                        }
                        break;

                    case 3:
                        List<AdoptionEvent> events = eventDao.getAllEvents();
                        if (events.isEmpty()) {
                            System.out.println("No events hosted.");
                        } else {
                            for (AdoptionEvent event : events) {
                                System.out.println(event);
                            }
                        }
                        break;

                    case 4:
                        System.out.print("Enter donor name: ");
                        String donorName = scanner.nextLine();
                        if (donorName == null || donorName.isEmpty() || !donorName.matches("[a-zA-Z ]+")) {
                            throw new NullReferenceException("Donor name cannot be empty or contain numbers.");
                        }

                        System.out.print("Enter donation amount: ");
                        BigDecimal amount = scanner.nextBigDecimal();
                        scanner.nextLine();

                        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                            throw new InsuffcientFundsException("Donation amount must be positive.");
                        }

                        LocalDateTime donationDateTime = LocalDateTime.now();
                        Donation cashDonation = new CashDonation(donorName, amount, donationDateTime);
                        donationDao.recordDonation(cashDonation);
                        System.out.println("Donation recorded successfully!");
                        break;

                    case 5:
                        System.out.print("Enter pet name: ");
                        String petName = scanner.nextLine();
                        if (petName == null || petName.isEmpty() || !petName.matches("[a-zA-Z ]+")) {
                            throw new NullReferenceException("Pet name cannot be empty or contain numbers.");
                        }

                        System.out.print("Enter pet age: ");
                        int petAge = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        if (petAge < 0) {
                            throw new InvalidPetAgeHandling("Pet age cannot be negative.");
                        }

                        System.out.print("Enter pet breed: ");
                        String petBreed = scanner.nextLine();
                        if (petBreed == null || petBreed.isEmpty() || !petBreed.matches("[a-zA-Z ]+")) {
                            throw new NullReferenceException("Pet breed cannot be empty or contain numbers.");
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
                        System.out.print("Enter pet ID to remove: ");
                        int petId = scanner.nextInt();
                        scanner.nextLine();
                        if (petId <= 0) {
                            throw new NullReferenceException("Pet ID cannot be negative.");
                        }
                        if (petDao.removePet(petId)) {
                            System.out.println("Pet removed successfully.");
                        } else {
                            System.out.println("Failed to remove pet.");
                        }
                        break;

                    case 7:
                        System.out.print("Enter event name: ");
                        String eventName = scanner.nextLine();
                        if (eventName == null || eventName.isEmpty() || !eventName.matches("[a-zA-Z0-9 ]+")) {
                            throw new NullReferenceException("Event name cannot be empty or invalid.");
                        }

                        System.out.print("Enter event date (YYYY-MM-DD): ");
                        String eventDateStr = scanner.nextLine();
                        if (!eventDateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            throw new NullReferenceException("Invalid event date format.");
                        }
                        LocalDate eventDate = LocalDate.parse(eventDateStr);

                        AdoptionEvent adoptionEvent = new AdoptionEvent(eventName, eventDate);
                        if (eventDao.hostEvent(adoptionEvent)) {
                            System.out.println("Event hosted successfully!");
                        } else {
                            System.out.println("Failed to host event.");
                        }
                        break;

                    case 8:
                        System.out.print("Enter Event ID to register in: ");
                        int eventId = scanner.nextInt();
                        scanner.nextLine();
                        if (eventId <= 0) {
                            throw new NullReferenceException("Event ID cannot be negative.");
                        }

                        System.out.print("Enter participant ID (pet id): ");
                        int participantId = scanner.nextInt();
                        scanner.nextLine();
                        if (participantId <= 0) {
                            throw new NullReferenceException("Participant ID cannot be negative.");
                        }

                        System.out.print("Enter participant name: ");
                        String participantName = scanner.nextLine();
                        if (participantName == null || participantName.isEmpty() || !participantName.matches("[a-zA-Z ]+")) {
                            throw new NullReferenceException("Participant name cannot be empty or contain numbers.");
                        }

                        if (eventDao.registration(eventId, participantId, participantName)) {
                            System.out.println("Participant registered successfully!");
                        } else {
                            System.out.println("Failed to register participant.");
                        }
                        break;

                    case 9:
                        eventDao.showAllParticipants();
                        break;

                    case 10:
                        List<Pet> availablePets = petDao.getAllNonAdoptedPets();
                        if (availablePets.isEmpty()) {
                            System.out.println("No pets available for adoption.");
                        } else {
                            for (Pet pets : availablePets) {
                                System.out.println(pets);
                            }
                        }
                        break;

                    case 11:

                        System.out.println("Available pets for adoption:");
                        List<Pet> availablePetsToAdopt = petDao.getAllNonAdoptedPets();
                        if (availablePetsToAdopt.isEmpty()) {
                            System.out.println("No pets available for adoption.");
                        } else {
                            for (Pet pets : availablePetsToAdopt) {
                                System.out.println(pets);
                            }
                        }
                        System.out.print("Enter pet ID to adopt: ");
                        int pet_id = scanner.nextInt();
                        scanner.nextLine();

                        if (pet_id <= 0) {
                            System.out.println("Invalid pet ID.");
                        } else {
                            boolean adopted = eventDao.adoptPet(pet_id);
                            if (adopted) {
                                System.out.println("I'm Grateful for Adopting me!");
                            } else {
                                System.out.println("Pet is already adopted or not found.");
                            }
                        }
                        break;

                    case 12:
                        System.out.println("The list of pets that are adopted : ");
                        List<Pet> adoptedpets = petDao.getAllAdoptedPets();
                        if(adoptedpets.isEmpty()){
                            System.out.println("There is none pets adopted : \n" );
                        }
                        for(Pet peting : adoptedpets){
                            System.out.println(peting);
                        }
                        break;
                    case 13:
                        System.out.println("Exiting... Thank you!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NullReferenceException | InsuffcientFundsException | InvalidPetAgeHandling e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage());
                scanner.nextLine(); // Clear scanner buffer
            }
        }
    }
}