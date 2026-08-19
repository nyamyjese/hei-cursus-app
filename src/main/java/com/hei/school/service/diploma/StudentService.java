package com.hei.school.service.diploma;

import com.hei.school.model.Course;
import com.hei.school.model.Grade;
import com.hei.school.repository.CourseRepository;
import com.hei.school.repository.GradeRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final CourseRepository courseRepository;
  private final GradeRepository gradeRepository;

  public boolean isGraduated(UUID studentId, UUID trackId) {
    List<Course> trackCourses = courseRepository.findByTrackId(trackId);

    for (Course course : trackCourses) {
      List<Grade> courseGrades =
          gradeRepository.findByStudentIdAndCourseId(studentId, course.getId());
      BigDecimal courseAverage = calculateCourseAverage(courseGrades);

      if (courseAverage.compareTo(new BigDecimal("10.00")) < 0) {
        return false;
      }
    }
    return true;
  }

  public boolean isYearValidated(UUID studentId, UUID trackId, int academicYear) {
    List<Course> yearlyCourses =
        courseRepository.findByTrackId(trackId).stream()
            .filter(course -> getYearFromSemester(course.getSemester()) == academicYear)
            .toList();

    if (yearlyCourses.isEmpty()) return false;

    BigDecimal totalScore = BigDecimal.ZERO;
    int validatedCredits = 0;

    for (Course course : yearlyCourses) {
      List<Grade> courseGrades =
          gradeRepository.findByStudentIdAndCourseId(studentId, course.getId());
      BigDecimal courseAverage = calculateCourseAverage(courseGrades);

      totalScore = totalScore.add(courseAverage);
      if (courseAverage.compareTo(new BigDecimal("10.00")) >= 0) {
        validatedCredits += course.getCredits();
      }
    }

    BigDecimal yearlyAverage =
        totalScore.divide(new BigDecimal(yearlyCourses.size()), 2, java.math.RoundingMode.HALF_UP);

    return (validatedCredits >= 30) && (yearlyAverage.compareTo(new BigDecimal("10.00")) >= 0);
  }

  private BigDecimal calculateCourseAverage(List<Grade> grades) {
    if (grades == null || grades.isEmpty()) return BigDecimal.ZERO;
    BigDecimal total = BigDecimal.ZERO;
    for (Grade grade : grades) {
      BigDecimal coeff = grade.getExam().getCoefficient();
      total = total.add(grade.getValue().multiply(coeff));
    }
    return total;
  }

  private int getYearFromSemester(int semester) {
    if (semester == 1 || semester == 2) return 1;
    if (semester == 3 || semester == 4) return 2;
    if (semester == 5 || semester == 6) return 3;
    throw new IllegalArgumentException("Semester : " + semester + "is invalid");
  }
}
