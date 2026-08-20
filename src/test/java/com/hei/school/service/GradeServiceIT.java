package com.hei.school.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hei.school.conf.TestcontainersInitializer;
import com.hei.school.model.Course;
import com.hei.school.model.CourseAssignment;
import com.hei.school.model.Exam;
import com.hei.school.model.Grade;
import com.hei.school.model.GradeHistory;
import com.hei.school.model.Group;
import com.hei.school.model.Promotion;
import com.hei.school.model.Role;
import com.hei.school.model.Session;
import com.hei.school.model.Student;
import com.hei.school.model.Teacher;
import com.hei.school.model.Track;
import com.hei.school.model.User;
import com.hei.school.repository.CourseAssignmentRepository;
import com.hei.school.repository.CourseRepository;
import com.hei.school.repository.ExamRepository;
import com.hei.school.repository.GradeHistoryRepository;
import com.hei.school.repository.GradeRepository;
import com.hei.school.repository.GroupRepository;
import com.hei.school.repository.PromotionRepository;
import com.hei.school.repository.StudentRepository;
import com.hei.school.repository.TeacherRepository;
import com.hei.school.repository.TrackRepository;
import com.hei.school.repository.UserRepository;
import com.hei.school.service.diploma.GradeService;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
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
public class GradeServiceIT {

  @Autowired private GradeService gradeService;

  @Autowired private UserRepository userRepository;
  @Autowired private TeacherRepository teacherRepository;
  @Autowired private StudentRepository studentRepository;
  @Autowired private TrackRepository trackRepository;
  @Autowired private CourseRepository courseRepository;
  @Autowired private GroupRepository groupRepository;
  @Autowired private PromotionRepository promotionRepository;
  @Autowired private CourseAssignmentRepository courseAssignmentRepository;
  @Autowired private ExamRepository examRepository;
  @Autowired private GradeRepository gradeRepository;
  @Autowired private GradeHistoryRepository gradeHistoryRepository;

  private Teacher testTeacher;
  private Teacher unauthorizedTeacher;
  private Grade testGrade;

  @BeforeEach
  void setUp() {

    Track track = new Track();
    track.setId(UUID.randomUUID());
    track.setCode("EL_TEST");
    track.setLabel("Ecosystème Logiciel Test");
    trackRepository.save(track);

    Promotion promotion = new Promotion();
    promotion.setId(UUID.randomUUID());
    promotion.setRef("Promo 2024_TEST");
    promotionRepository.save(promotion);

    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setTrack(track);
    group.setRef("K1_TEST");
    group.setYearLevel(1);
    group.setPromotion(promotion);
    groupRepository.save(group);

    User user1 = new User();
    user1.setId(UUID.randomUUID());
    user1.setFirstName("Prof");
    user1.setLastName("Autorise");
    user1.setEmail("prof.autorise.test@hei.school");
    user1.setPassword("dummyPassword");
    user1.setRole(Role.TEACHER);
    userRepository.save(user1);

    testTeacher = new Teacher();
    testTeacher.setId(UUID.randomUUID());
    testTeacher.setUser(user1);
    teacherRepository.save(testTeacher);

    User user2 = new User();
    user2.setId(UUID.randomUUID());
    user2.setFirstName("Prof");
    user2.setLastName("Pirate");
    user2.setEmail("prof.non.autorise.test@hei.school");
    user2.setPassword("dummyPassword");
    user2.setRole(Role.TEACHER);
    userRepository.save(user2);

    unauthorizedTeacher = new Teacher();
    unauthorizedTeacher.setId(UUID.randomUUID());
    unauthorizedTeacher.setUser(user2);
    teacherRepository.save(unauthorizedTeacher);

    Course course = new Course();
    course.setId(UUID.randomUUID());
    course.setTrack(track);
    course.setRef("PROG4_TEST");
    course.setTitle("Programmation 4");
    course.setCredits(6);
    course.setSemester(4);
    courseRepository.save(course);

    CourseAssignment assignment = new CourseAssignment();
    assignment.setId(UUID.randomUUID());
    assignment.setCourse(course);
    assignment.setTeacher(testTeacher);
    assignment.setGroup(group);
    courseAssignmentRepository.save(assignment);

    User studentUser = new User();
    studentUser.setId(UUID.randomUUID());
    studentUser.setFirstName("Jesse");
    studentUser.setLastName("Student");
    studentUser.setEmail("student.test@hei.school");
    studentUser.setPassword("dummyPassword");
    studentUser.setRole(Role.STUDENT);
    userRepository.save(studentUser);

    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setUser(studentUser);
    student.setStd("STD21001_TEST");
    student.setTrack(track);
    studentRepository.save(student);

    Exam exam = new Exam();
    exam.setId(UUID.randomUUID());
    exam.setCourse(course);
    exam.setDateExams(Instant.now());
    exam.setCoefficient(new BigDecimal("1.00"));
    exam.setSession(Session.NORMALE);
    examRepository.save(exam);

    testGrade = new Grade();
    testGrade.setId(UUID.randomUUID());
    testGrade.setExam(exam);
    testGrade.setStudent(student);
    testGrade.setValue(new BigDecimal("12.00"));
    gradeRepository.save(testGrade);
  }

  @Test
  void updateGrade_creates_history_and_updates_value_when_teacher_is_authorized() {
    BigDecimal newValue = new BigDecimal("15.50");
    String reason = "Réclamation acceptée";

    Grade updatedGrade =
        gradeService.updateGrade(testGrade.getId(), newValue, reason, testTeacher.getId());

    assertEquals(newValue, updatedGrade.getValue());

    List<GradeHistory> histories = gradeHistoryRepository.findAll();
    assertEquals(1, histories.size());

    GradeHistory history = histories.getFirst();
    assertEquals(testGrade.getId(), history.getGrade().getId());
    assertEquals(new BigDecimal("12.00"), history.getOldValue());
    assertEquals(newValue, history.getNewValue());
    assertEquals(reason, history.getReason());
    assertNotNull(history.getModifiedAt());

    assertEquals(testTeacher.getUser().getId(), history.getModifiedBy().getId());
  }

  @Test
  void updateGrade_throws_exception_when_teacher_is_not_authorized() {
    BigDecimal newValue = new BigDecimal("15.50");
    String reason = "Tentative de triche";

    SecurityException exception =
        assertThrows(
            SecurityException.class,
            () ->
                gradeService.updateGrade(
                    testGrade.getId(), newValue, reason, unauthorizedTeacher.getId()));

    assertEquals("Teacher is not assigned to this course", exception.getMessage());
  }
}
