package com.hei.school.repository;

import com.hei.school.model.Grade;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GradeRepository extends JpaRepository<Grade, UUID> {

  List<Grade> findByStudentId(UUID studentId);

  Optional<Grade> findByExamIdAndStudentId(UUID examId, UUID studentId);

  List<Grade> findByExamId(UUID examId);

  @Query(
      """
      select g from Grade g
      where g.student.id = :studentId
        and g.exam.course.id = :courseId
      """)
  List<Grade> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
}
