package com.hei.school.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_membership")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMembership {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @ManyToOne(optional = false)
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;

  @Column(nullable = false)
  private LocalDate startDate;

  @Column private LocalDate endDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GroupMembership other)) return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
