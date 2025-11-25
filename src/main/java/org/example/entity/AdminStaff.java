package org.example.entity;
import jakarta.persistence.*;

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

    public AdminStaff() {
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
}
