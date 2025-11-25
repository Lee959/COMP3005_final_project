package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long equipmentId;

    @Column(nullable = false, length = 100)
    private String name;           // e.g., "Treadmill #1"

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(length = 20)
    private String status;         // e.g., "OK", "OutOfOrder"

    public Equipment() {
    }

    public Equipment(String name, Room room, String status) {
        this.name = name;
        this.room = room;
        this.status = status;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
