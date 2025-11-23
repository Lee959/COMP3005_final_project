package org.example.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_metric")
public class HealthMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metric_id")
    private Long metricId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "recorded_date")
    private LocalDateTime recordedDate;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    @Column(name = "heart_rate")
    private Integer heartRate;

    @Column(name = "body_fat_percentage", precision = 5, scale = 2)
    private BigDecimal bodyFatPercentage;

    // Constructors
    public HealthMetric() {
        this.recordedDate = LocalDateTime.now();
    }

    public HealthMetric(Member member) {
        this();
        this.member = member;
    }

    // Getter and Setter

    public Long getMetricId() {
        return metricId;
    }

    public void setMetricId(Long metricId) {
        this.metricId = metricId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(LocalDateTime recordedDate) {
        this.recordedDate = recordedDate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public BigDecimal getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(BigDecimal bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    @Override
    public String toString() {
        return "HealthMetric{" +
                "metricId=" + metricId +
                ", member=" + member +
                ", recordedDate=" + recordedDate +
                ", weight=" + weight +
                ", heartRate=" + heartRate +
                ", bodyFatPercentage=" + bodyFatPercentage +
                '}';
    }
}
