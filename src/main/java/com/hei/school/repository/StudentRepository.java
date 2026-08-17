package com.hei.school.repository;

import com.hei.school.model.Student;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, UUID> {

  Optional<Student> findByUserId(UUID userId);

  Optional<Student> findByStd(String std);

  List<Student> findByTrackId(UUID trackId);

  @Query(
      """
      select s from Student s
      join GroupMembership gm on gm.student = s
      where gm.endDate is null
        and gm.group.promotion.id = :promotionId
      """)
  List<Student> findCurrentlyInPromotion(UUID promotionId);
}
