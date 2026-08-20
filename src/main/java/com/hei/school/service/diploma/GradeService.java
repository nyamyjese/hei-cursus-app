package com.hei.school.service.diploma;

import com.hei.school.model.Grade;
import com.hei.school.model.GradeHistory;
import com.hei.school.repository.CourseAssignmentRepository;
import com.hei.school.repository.GradeHistoryRepository;
import com.hei.school.repository.GradeRepository;
import com.hei.school.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeService {

  private final GradeRepository gradeRepository;
  private final GradeHistoryRepository gradeHistoryRepository;
  private final CourseAssignmentRepository courseAssignmentRepository;
  private final TeacherRepository teacherRepository;

  @Transactional
  public Grade updateGrade(UUID gradeId, BigDecimal newValue, String reason, UUID teacherId) {
    Grade grade =
        gradeRepository
            .findById(gradeId)
            .orElseThrow(() -> new IllegalArgumentException("Grade not found"));

    boolean isAssigned =
        courseAssignmentRepository.existsByTeacherIdAndCourseId(
            teacherId, grade.getExam().getCourse().getId());
    if (!isAssigned) {
      throw new SecurityException("Teacher is not assigned to this course");
    }

    var teacher =
        teacherRepository
            .findById(teacherId)
            .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

    GradeHistory history = new GradeHistory();
    history.setGrade(grade);
    history.setOldValue(grade.getValue());
    history.setNewValue(newValue);
    history.setReason(reason);
    history.setModifiedBy(teacher.getUser());
    history.setModifiedAt(Instant.now());
    gradeHistoryRepository.save(history);

    grade.setValue(newValue);
    return gradeRepository.save(grade);
  }
}
