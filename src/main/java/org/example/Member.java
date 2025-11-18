package org.example;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int member_id;

    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private Date date_of_birth;
    private char gender;
    private String phone_number;

    @Column(updatable = false)
    private Timestamp registeration_date;

    // Constructor
    public Member(){

    }
    public Member(String email, String password, String first_name, String last_name, Date dob, char gender, String phone_number) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = dob;
        this.gender = gender;
        this.phone_number = phone_number;
    }

    @PrePersist
    protected void onCreate() {
        this.registeration_date = new Timestamp(System.currentTimeMillis());
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

    public Date getDate_of_() {
        return date_of_birth;
    }

    public void setDate_of_(Date date_of_) {
        this.date_of_birth = date_of_;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Timestamp getRegisteration_date() {
        return registeration_date;
    }

    public void setRegisteration_date(Timestamp registeration_date) {
        this.registeration_date = registeration_date;
    }

    @Override
    public String toString() {
        return "Member{" +
                "member_id=" + member_id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", gender=" + gender +
                ", phone_number='" + phone_number + '\'' +
                ", registeration_date=" + registeration_date +
                '}';
    }
}
