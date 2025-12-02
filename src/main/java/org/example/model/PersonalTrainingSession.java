package org.example.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;


@Entity
@Table(name = "personal_training_sessions")
public class PersonalTrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "session_date")
    private LocalDate sessionDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(length = 20)
    private String status; // Scheduled, Completed, Cancelled

    // Constructors
    private void init() {
        this.status = "Scheduled";
    }


    // Constructor For Testing
    public PersonalTrainingSession() {
        init();
        this.member = generateMember();
        this.trainer = generateTrainer();
        this.room = generateRoom();
        this.sessionDate = generateRandomSessionDate();
        this.startTime = generateRandomStartTime();
        this.endTime = generateRandomEndTime(startTime);
    }

    public PersonalTrainingSession(Member member, Trainer trainer, Room room) {
        init();
        this.sessionDate = generateRandomSessionDate();
        this.startTime = generateRandomStartTime();
        this.endTime = generateRandomEndTime(startTime);
    }


    public PersonalTrainingSession(Long sessionId, Member member, Trainer trainer, Room room, LocalDate sessionDate, LocalTime startTime, LocalTime endTime, String status) {
        init();
        this.sessionId = sessionId;
        this.member = member;
        this.trainer = trainer;
        this.room = room;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    private  LocalDate generateRandomSessionDate() {
        Random random = new Random();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return startDate.plusDays(random.nextInt((int) days + 1));
    }

    private LocalTime generateRandomStartTime() {
        Random random = new Random();
        int hour = 8 + random.nextInt(11);
        int minute = random.nextInt(60);
        return LocalTime.of(hour, minute);
    }

    private LocalTime generateRandomEndTime(LocalTime startTime) {
        Random random = new Random();
        int minEndHour = startTime.getHour();
        int hour = minEndHour + random.nextInt(18 - minEndHour); // ensure end time before 18:00
        int minute = random.nextInt(60);
        LocalTime endTime = LocalTime.of(hour, minute);
        if (endTime.isBefore(startTime.plusMinutes(30))) {
            endTime = startTime.plusMinutes(30);
        }
        if (endTime.isAfter(LocalTime.of(18, 0))) {
            endTime = LocalTime.of(18, 0);
        }
        return endTime;
    }

    // 4. Generate a random status
    public static String generateRandomStatus() {
        Random random = new Random();
        String[] statuses = {"Scheduled", "Completed", "Cancelled"};
        return statuses[random.nextInt(statuses.length)];
    }

    // Getter and Setter
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PersonalTrainingSession{" +
                "sessionId=" + sessionId +
                ", member=" + member +
                ", trainer=" + trainer +
                ", room=" + room +
                ", sessionDate=" + sessionDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                '}';
    }

    private Member generateMember() {
        Member generated_member = new Member();
        return generated_member;
    }

    private Trainer generateTrainer() {
        Trainer generated_trainer = new Trainer();
        return generated_trainer;
    }

    private Room generateRoom() {
        Room generated_room = new Room();
        return generated_room;
    }


}
