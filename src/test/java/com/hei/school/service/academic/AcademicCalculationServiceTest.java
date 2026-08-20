package com.hei.school.service.academic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.hei.school.model.Course;
import com.hei.school.model.Exam;
import com.hei.school.model.Grade;
import com.hei.school.model.Student;
import com.hei.school.repository.GradeRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AcademicCalculationServiceTest {

    @Mock GradeRepository gradeRepository;
    @InjectMocks AcademicCalculationService service;

    private Student student;
    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(UUID.randomUUID());

        course1 = new Course();
        course1.setId(UUID.randomUUID());
        course1.setCredits(20);

        course2 = new Course();
        course2.setId(UUID.randomUUID());
        course2.setCredits(10);
    }

    private Grade createGrade(Course course, String value) {
        Exam exam = new Exam();
        exam.setCourse(course);
        exam.setCoefficient(BigDecimal.ONE);

        Grade grade = new Grade();
        grade.setExam(exam);
        grade.setValue(new BigDecimal(value));
        return grade;
    }

    @Test
    void isYearValidated_returns_true_when_conditions_met() {
        when(gradeRepository.findByStudentIdAndCourseId(student.getId(), course1.getId()))
                .thenReturn(List.of(createGrade(course1, "12.00")));
        when(gradeRepository.findByStudentIdAndCourseId(student.getId(), course2.getId()))
                .thenReturn(List.of(createGrade(course2, "14.00")));

        assertTrue(service.isYearValidated(student, List.of(course1, course2)));
    }

    @Test
    void isYearValidated_returns_false_when_credits_missing() {

        when(gradeRepository.findByStudentIdAndCourseId(student.getId(), course1.getId()))
                .thenReturn(List.of(createGrade(course1, "8.00")));
        when(gradeRepository.findByStudentIdAndCourseId(student.getId(), course2.getId()))
                .thenReturn(List.of(createGrade(course2, "14.00")));

        assertFalse(service.isYearValidated(student, List.of(course1, course2)));
    }

    @Test
    void needsRetake_returns_courses_under_10() {
        when(gradeRepository.findByStudentIdAndCourseId(student.getId(), course1.getId()))
                .thenReturn(List.of(createGrade(course1, "8.00")));
        when(gradeRepository.findByStudentIdAndCourseId(student.getId(), course2.getId()))
                .thenReturn(List.of(createGrade(course2, "12.00")));

        List<Course> retakes = service.needsRetake(student, List.of(course1, course2));

        assertEquals(1, retakes.size());
        assertEquals(course1.getId(), retakes.get(0).getId());
    }

    @Test
    void isGraduated_returns_true_only_if_all_courses_validated() {

        when(gradeRepository.findByStudentIdAndCourseId(student.getId(), course1.getId()))
                .thenReturn(List.of(createGrade(course1, "12.00")));
        assertTrue(service.isGraduated(student, List.of(course1)));

        when(gradeRepository.findByStudentIdAndCourseId(student.getId(), course1.getId()))
                .thenReturn(List.of());
        assertFalse(service.isGraduated(student, List.of(course1)));
    }
}