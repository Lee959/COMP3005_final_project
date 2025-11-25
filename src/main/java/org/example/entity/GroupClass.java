package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "group_classes")
public class GroupClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Column(nullable = false, length = 150)
    private String name;                 // e.g., "Morning Yoga"

    @Column(length = 255)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "class_date", nullable = false)
    private LocalDate classDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Integer capacity;

    public GroupClass() {
    }

    public GroupClass(String name, String description, Trainer trainer,
                      Room room, LocalDate classDate,
                      LocalTime startTime, LocalTime endTime,
                      Integer capacity) {
        this.name = name;
        this.description = description;
        this.trainer = trainer;
        this.room = room;
        this.classDate = classDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
    }

    public Long getClassId() {
        return classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getClassDate() {
        return classDate;
    }

    public void setClassDate(LocalDate classDate) {
        this.classDate = classDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "GroupClass{" +
                "classId=" + classId +
                ", name='" + name + '\'' +
                ", trainer=" + trainer +
                ", room=" + room +
                ", classDate=" + classDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", capacity=" + capacity +
                '}';
    }
}
