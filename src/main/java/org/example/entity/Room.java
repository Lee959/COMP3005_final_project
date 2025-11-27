package org.example.entity;

import jakarta.persistence.*;
import java.util.Random;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_name", nullable = false, length = 100)
    private String roomName;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    // -------------------- Testing Constructor --------------------
    public Room() {
        this.roomName = generateRandomRoomName();
        this.capacity = generateRandomCapacity();
    }

    // -------------------- Helper Arrays --------------------
    private static final String[] ROOM_NAMES = {
            "Studio A", "Studio B", "Yoga Room", "Spin Studio",
            "Cardio Room", "Weight Room", "Training Hall"
    };

    // -------------------- Helper Methods --------------------
    private String generateRandomRoomName() {
        Random random = new Random();
        return ROOM_NAMES[random.nextInt(ROOM_NAMES.length)];
    }

    private int generateRandomCapacity() {
        Random random = new Random();
        return 5 + random.nextInt(25);   // capacity 5–29
    }

    public Long getRoomId() {
        return roomId;
    }
    public String getRoomName() {
        return roomName;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", capacity=" + capacity +
                '}';
    }

}
