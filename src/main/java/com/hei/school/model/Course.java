package com.hei.school.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String ref;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private int credits;

  @Column(nullable = false)
  private int semester;

  @ManyToOne(optional = false)
  @JoinColumn(name = "track_id", nullable = false)
  private Track track;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Course other)) return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
