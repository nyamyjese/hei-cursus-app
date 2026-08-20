package com.hei.school.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hei.school.conf.TestcontainersInitializer;
import com.hei.school.model.Course;
import com.hei.school.model.Exam;
import com.hei.school.model.Grade;
import com.hei.school.model.Role;
import com.hei.school.model.Session;
import com.hei.school.model.Student;
import com.hei.school.model.Track;
import com.hei.school.model.User;
import com.hei.school.repository.CourseRepository;
import com.hei.school.repository.ExamRepository;
import com.hei.school.repository.GradeRepository;
import com.hei.school.repository.StudentRepository;
import com.hei.school.repository.TrackRepository;
import com.hei.school.repository.UserRepository;
import com.hei.school.service.diploma.StudentService;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ContextConfiguration(initializers = TestcontainersInitializer.class)
@Transactional
public class StudentServiceIT {

  @Autowired private StudentService studentService;

  @Autowired private UserRepository userRepository;
  @Autowired private TrackRepository trackRepository;
  @Autowired private StudentRepository studentRepository;
  @Autowired private CourseRepository courseRepository;
  @Autowired private ExamRepository examRepository;
  @Autowired private GradeRepository gradeRepository;

  private Student testStudent;
  private Track progTrack;
  private Course courseCommun;
  private Course courseProg;

  @BeforeEach
  void setUp() {
    trackRepository
        .findByCode("COMMUN")
        .ifPresent(
            t -> {
              t.setCode("HIDDEN_" + UUID.randomUUID().toString().substring(0, 5));
              trackRepository.save(t);
            });

    trackRepository.findByCode("FORCE_FLUSH_HIBERNATE");

    Track communTrack = new Track();
    communTrack.setId(UUID.randomUUID());
    communTrack.setCode("COMMUN");
    communTrack.setLabel("Tronc Commun Test");
    trackRepository.save(communTrack);

    progTrack = new Track();
    progTrack.setId(UUID.randomUUID());
    progTrack.setCode("PROG_STUDENT_TEST");
    progTrack.setLabel("Programmation Test");
    trackRepository.save(progTrack);

    User studentUser = new User();
    studentUser.setId(UUID.randomUUID());
    studentUser.setFirstName("Jane");
    studentUser.setLastName("Doe");
    studentUser.setEmail("jane.student.test@hei.school");
    studentUser.setPassword("dummyPassword");
    studentUser.setRole(Role.STUDENT);
    userRepository.save(studentUser);

    testStudent = new Student();
    testStudent.setId(UUID.randomUUID());
    testStudent.setUser(studentUser);
    testStudent.setStd("STD21002_TEST");
    testStudent.setTrack(progTrack);
    studentRepository.save(testStudent);

    courseCommun = new Course();
    courseCommun.setId(UUID.randomUUID());
    courseCommun.setTrack(communTrack);
    courseCommun.setRef("MATH_COMMUN_TEST");
    courseCommun.setTitle("Mathématiques");
    courseCommun.setCredits(15);
    courseCommun.setSemester(1);
    courseRepository.save(courseCommun);

    courseProg = new Course();
    courseProg.setId(UUID.randomUUID());
    courseProg.setTrack(progTrack);
    courseProg.setRef("JAVA_PROG_TEST");
    courseProg.setTitle("Programmation Orientée Objet");
    courseProg.setCredits(15);
    courseProg.setSemester(2);
    courseRepository.save(courseProg);

    Exam examCommun = new Exam();
    examCommun.setId(UUID.randomUUID());
    examCommun.setCourse(courseCommun);
    examCommun.setDateExams(Instant.now());
    examCommun.setCoefficient(new BigDecimal("1.00"));
    examCommun.setSession(Session.NORMALE);
    examRepository.save(examCommun);

    Exam examProg = new Exam();
    examProg.setId(UUID.randomUUID());
    examProg.setCourse(courseProg);
    examProg.setDateExams(Instant.now());
    examProg.setCoefficient(new BigDecimal("1.00"));
    examProg.setSession(Session.NORMALE);
    examRepository.save(examProg);

    Grade gradeCommun = new Grade();
    gradeCommun.setId(UUID.randomUUID());
    gradeCommun.setExam(examCommun);
    gradeCommun.setStudent(testStudent);
    gradeCommun.setValue(new BigDecimal("12.00"));
    gradeRepository.save(gradeCommun);

    Grade gradeProg = new Grade();
    gradeProg.setId(UUID.randomUUID());
    gradeProg.setExam(examProg);
    gradeProg.setStudent(testStudent);
    gradeProg.setValue(new BigDecimal("14.00"));
    gradeRepository.save(gradeProg);
  }

  @Test
  void isGraduated_returns_true_when_all_courses_average_is_above_10() {
    boolean graduated = studentService.isGraduated(testStudent.getId(), progTrack.getId());
    assertTrue(graduated, "L'étudiant devrait être diplômé");
  }

  @Test
  void isYearValidated_returns_true_when_year_1_has_30_credits_and_average_above_10() {
    boolean yearValidated =
        studentService.isYearValidated(testStudent.getId(), progTrack.getId(), 1);
    assertTrue(yearValidated, "L'année 1 devrait être validée");
  }

  @Test
  void calculateGeneralAverage_returns_correct_average() {
    BigDecimal expectedAverage = new BigDecimal("13.00");
    BigDecimal actualAverage =
        studentService.calculateGeneralAverage(testStudent.getId(), progTrack.getId());

    assertEquals(expectedAverage, actualAverage);
  }
}
