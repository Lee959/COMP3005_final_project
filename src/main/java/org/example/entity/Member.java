package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int member_id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String first_name;

    @Column(nullable = false, length = 50)
    private String last_name;

    @Column(length = 20)
    private String gender;

    @Column(updatable = false)
    private LocalDateTime registeration_date;

    // One member can have multiple fitness goals
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FitnessGoal> fitnessGoals = new HashSet<>();

    // One member can have multiple health metrics
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HealthMetric> healthMetrics = new HashSet<>();

    // One member can have multiple training sessions
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<PersonalTrainingSession> trainingSessions = new HashSet<>();



    // Constructor
    private void init() {
        this.registeration_date = LocalDateTime.now();
    }

    // Constructor for Testing
    public Member(){
        init();
        this.password = generateRandomPassword();
        this.first_name = generateRandomFirstName();
        this.last_name = generateRandomLastName();
        this.gender = generateGender();
        this.email = generateEmail();
    }

    public Member(String email, String password, String first_name, String last_name, String gender) {
        init();
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    // Getters and Setters
    public int getMember_id() {
        return member_id;
    }
    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public LocalDateTime getRegisteration_date() {
        return registeration_date;
    }
    public void setRegisteration_date(LocalDateTime registeration_date) {
        this.registeration_date = registeration_date;
    }

    // Helper Function for Testing
    private static final String[] FIRST_NAMES = { "Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Hannah" };
    private static final String[] LAST_NAMES = { "Smith", "Johnson", "Brown", "Williams", "Jones", "Miller", "Davis"};
    private static final String[] GENDERS = {"Male", "Female", "Prefer Not to Say"};

    private String generateRandomPassword() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }
    private String generateEmail() {
        return (first_name + "_" + last_name + "@example.com").toLowerCase();
    }
    private String generateRandomFirstName() {
        Random random = new Random();
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }
    private String generateRandomLastName() {
        Random random = new Random();
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }
    private String generateGender() {
        Random random = new Random();
        return GENDERS[random.nextInt(GENDERS.length)];
    }


    @Override
    public String toString() {
        return "Member{" +
                "member_id=" + member_id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender='" + gender + '\'' +
                ", registeration_date=" + registeration_date +
                ", fitnessGoals=" + fitnessGoals +
                ", healthMetrics=" + healthMetrics +
                ", trainingSessions=" + trainingSessions +
                '}';
    }
}
