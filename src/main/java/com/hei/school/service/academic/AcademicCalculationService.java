package com.hei.school.service.academic;

import com.hei.school.model.Course;
import com.hei.school.model.Grade;
import com.hei.school.model.Student;
import com.hei.school.repository.GradeRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AcademicCalculationService {

  private final GradeRepository gradeRepository;

  public boolean isYearValidated(Student student, List<Course> yearCourses) {
    int totalCredits = yearCourses.stream().mapToInt(Course::getCredits).sum();
    int validatedCredits = 0;
    BigDecimal weightedSum = BigDecimal.ZERO;
    BigDecimal coeffSum = BigDecimal.ZERO;

    for (Course course : yearCourses) {
      var grades = gradeRepository.findByStudentIdAndCourseId(student.getId(), course.getId());
      double average = courseAverage(grades);
      if (average >= 10.0) {
        validatedCredits += course.getCredits();
      }
      weightedSum = weightedSum.add(BigDecimal.valueOf(average * course.getCredits()));
      coeffSum = coeffSum.add(BigDecimal.valueOf(course.getCredits()));
    }

    double generalAverage =
        coeffSum.compareTo(BigDecimal.ZERO) > 0
            ? weightedSum.divide(coeffSum, 2, RoundingMode.HALF_UP).doubleValue()
            : 0.0;

    return validatedCredits >= 30 && generalAverage >= 10.0;
  }

  public List<Course> needsRetake(Student student, List<Course> yearCourses) {
    return yearCourses.stream()
        .filter(
            course -> {
              var grades =
                  gradeRepository.findByStudentIdAndCourseId(student.getId(), course.getId());
              return courseAverage(grades) < 10.0;
            })
        .toList();
  }

  public boolean isGraduated(Student student, List<Course> allProgramCourses) {
    return allProgramCourses.stream()
        .allMatch(
            course -> {
              var grades =
                  gradeRepository.findByStudentIdAndCourseId(student.getId(), course.getId());
              return courseAverage(grades) >= 10.0;
            });
  }

  private double courseAverage(List<Grade> grades) {
    if (grades.isEmpty()) return 0.0;
    BigDecimal weightedSum = BigDecimal.ZERO;
    BigDecimal coeffSum = BigDecimal.ZERO;
    for (Grade g : grades) {
      var coeff = g.getExam().getCoefficient();
      weightedSum = weightedSum.add(g.getValue().multiply(coeff));
      coeffSum = coeffSum.add(coeff);
    }
    return coeffSum.compareTo(BigDecimal.ZERO) > 0
        ? weightedSum.divide(coeffSum, 2, RoundingMode.HALF_UP).doubleValue()
        : 0.0;
  }
}
