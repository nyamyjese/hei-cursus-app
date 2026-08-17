package com.hei.school.repository;

import com.hei.school.model.Exam;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExamRepository extends JpaRepository<Exam, UUID> {

  List<Exam> findByCourseId(UUID courseId);

  @Query("select coalesce(sum(e.coefficient), 0) from Exam e where e.course.id = :courseId")
  BigDecimal sumCoefficientsByCourseId(UUID courseId);
}
