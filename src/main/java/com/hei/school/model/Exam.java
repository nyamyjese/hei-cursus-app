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
@Table(name = "exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @Column(nullable = false)
  private Instant dateExams;

  @Column(nullable = false, precision = 3, scale = 2)
  private BigDecimal coefficient;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private Session session = Session.NORMALE;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Exam other)) return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
