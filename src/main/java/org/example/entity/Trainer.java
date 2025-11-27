package org.example.entity;
import jakarta.persistence.*;
import java.util.Random;

@Entity
@Table(name = "trainers")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long trainerId;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String specialization;   // e.g., Strength, Cardio, Yoga

    // Constructor for Testing
    public Trainer() {

        this.firstName = generateRandomFirstName();
        this.lastName = generateRandomLastName();
        this.email = generateEmail();
        this.phoneNumber = generateRandomPhone();
        this.specialization = generateRandomSpecialization();
    }

    public Trainer(String firstName, String lastName, String email,
                   String phoneNumber, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "trainerId=" + trainerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }

    // -------------------- Helper Data for Testing --------------------
    private static final String[] FIRST_NAMES = {
            "Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Hannah"
    };
    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Williams", "Jones", "Miller", "Davis"
    };
    private static final String[] SPECIALIZATIONS = {
            "Strength Training", "Cardio", "Yoga", "Pilates", "CrossFit", "HIIT"
    };

    // -------------------- Helper Methods --------------------
    private String generateRandomFirstName() {
        Random random = new Random();
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    private String generateRandomLastName() {
        Random random = new Random();
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    private String generateEmail() {
        return (firstName + "_" + lastName + "@trainer.com").toLowerCase();
    }

    private String generateRandomPhone() {
        Random random = new Random();
        int suffix = 1000 + random.nextInt(9000);
        return "613-555-" + suffix;
    }

    private String generateRandomSpecialization() {
        Random random = new Random();
        return SPECIALIZATIONS[random.nextInt(SPECIALIZATIONS.length)];
    }

}
