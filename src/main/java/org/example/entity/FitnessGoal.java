package org.example.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "fitness_goal")
public class FitnessGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "goal_type", length = 100)
    private String goal_type;

    @Column(name = "target_value", precision = 10, scale = 2)
    private BigDecimal targetValue;

    @Column(name = "target_date")
    private Date targetDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    // Constructors
    public FitnessGoal() {
        this.createdDate = LocalDateTime.now();
    }

    public FitnessGoal(Member member, String goal_type, BigDecimal targetValue) {
        this();
        this.member = member;
        this.goal_type = goal_type;
        this.targetValue = targetValue;
        this.targetDate = randomFutureDate();
    }

    public FitnessGoal(Member member, String goal_type, BigDecimal targetValue, Date targetDate) {
        this();
        this.member = member;
        this.goal_type = goal_type;
        this.targetValue = targetValue;
        this.targetDate = targetDate;
    }

    private Date randomFutureDate() {
        Date now = new Date(); // current date
        long nowMillis = now.getTime();
        long oneYearLaterMillis = nowMillis + 365L * 24 * 60 * 60 * 1000;
        long randomMillis = ThreadLocalRandom.current().nextLong(nowMillis + 1, oneYearLaterMillis);
        Date randomFutureDate = new Date(randomMillis);

        return randomFutureDate;
    }

    // Getter and Setter
    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getGoal_type() {
        return goal_type;
    }

    public void setGoal_type(String goal_type) {
        this.goal_type = goal_type;
    }

    public BigDecimal getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(BigDecimal targetValue) {
        this.targetValue = targetValue;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "FitnessGoal{" +
                "goalId=" + goalId +
                ", member=" + member +
                ", goal_type='" + goal_type + '\'' +
                ", targetValue=" + targetValue +
                ", targetDate=" + targetDate +
                ", createdDate=" + createdDate +
                '}';
    }
}
