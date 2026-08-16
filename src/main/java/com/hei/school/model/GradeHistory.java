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
@Table(name = "grade_histories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "grade_id", nullable = false)
  private Grade grade;

  @Column(nullable = false, precision = 4, scale = 2)
  private BigDecimal oldValue;

  @Column(nullable = false, precision = 4, scale = 2)
  private BigDecimal newValue;

  @Column(nullable = false)
  private String reason;

  @ManyToOne(optional = false)
  @JoinColumn(name = "modified_by_user_id", nullable = false)
  private User modifiedBy;

  @Column(nullable = false)
  private Instant modifiedAt;

  @PrePersist
  void onCreate() {
    this.modifiedAt = Instant.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GradeHistory other)) return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
