package org.example.entity;
import jakarta.persistence.*;
import java.util.Random;


@Entity
@Table(name = "admin_staff")
public class AdminStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 50)
    private String role;   // e.g., "Manager", "Front Desk"

    // For testing purpose
    public AdminStaff() {
        this.name = generateRandomName();
        this.email = generateEmail();
        this.role = generateRandomRole();
    }


    public AdminStaff(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public Long getAdminId() {
        return adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AdminStaff{" +
                "adminId=" + adminId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


    // Helper Function for Testing
    private static final String[] FIRST_NAMES = {
            "Sophia", "Liam", "Olivia", "Noah", "Emma", "Lucas", "Ava", "Mason"
    };

    private static final String[] LAST_NAMES = {
            "Clark", "Baker", "Wright", "Hill", "Adams", "Bennett"
    };

    private static final String[] ROLES = {
            "Manager", "Front Desk", "Billing", "Support"
    };

    // -------------------- Helper Methods --------------------
    private String generateRandomName() {
        Random random = new Random();
        String first = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String last = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return first + " " + last;
    }

    private String generateEmail() {
        return name.replace(" ", "_").toLowerCase() + "@admin.com";
    }

    private String generateRandomRole() {
        Random random = new Random();
        return ROLES[random.nextInt(ROLES.length)];
    }

}
