package com.hei.school.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String ref;

  @Column(nullable = false)
  private int yearLevel;

  @ManyToOne(optional = false)
  @JoinColumn(name = "promotion_id", nullable = false)
  private Promotion promotion;

  @ManyToOne(optional = false)
  @JoinColumn(name = "track_id", nullable = false)
  private Track track;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Group other)) return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
