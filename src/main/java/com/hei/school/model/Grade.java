package com.hei.school.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "grades",
    uniqueConstraints = @UniqueConstraint(columnNames = {"exam_id", "student_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "exam_id", nullable = false)
  private Exam exam;

  @ManyToOne(optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @Column(nullable = false, precision = 4, scale = 2)
  private BigDecimal value;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant updatedAt;

  @PrePersist
  void onCreate() {
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  void onUpdate() {
    this.updatedAt = Instant.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Grade other)) return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
