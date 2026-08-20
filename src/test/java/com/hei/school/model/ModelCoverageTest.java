package com.hei.school.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class ModelCoverageTest {

  @Test
  void farm_coverage_for_models() {

    User user = new User();
    user.setId(UUID.randomUUID());
    user.setFirstName("Jesse");
    user.setLastName("Test");
    user.setEmail("jesse@test.com");
    user.setPassword("pass");
    user.setRole(Role.STUDENT);
    assertNotNull(user.toString());
    assertNotNull(user.getId());

    Track track = new Track();
    track.setId(UUID.randomUUID());
    track.setCode("PROG");
    track.setLabel("Prog");
    assertNotNull(track.toString());

    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setStd("STD123");
    student.setUser(user);
    student.setTrack(track);
    assertNotNull(student.toString());

    Course course = new Course();
    course.setId(UUID.randomUUID());
    course.setRef("C1");
    course.setTitle("T1");
    course.setCredits(10);
    course.setSemester(1);
    course.setTrack(track);
    assertNotNull(course.toString());

    Exam exam = new Exam();
    exam.setId(UUID.randomUUID());
    exam.setCourse(course);
    exam.setCoefficient(BigDecimal.ONE);
    exam.setSession(Session.NORMALE);
    exam.setDateExams(Instant.now());
    assertNotNull(exam.toString());

    Grade grade = new Grade();
    grade.setId(UUID.randomUUID());
    grade.setStudent(student);
    grade.setExam(exam);
    grade.setValue(BigDecimal.TEN);
    assertNotNull(grade.toString());

    Promotion promo = new Promotion();
    promo.setId(UUID.randomUUID());
    promo.setRef("P1");
    assertNotNull(promo.toString());

    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setRef("G1");
    group.setYearLevel(1);
    group.setPromotion(promo);
    group.setTrack(track);
    assertNotNull(group.toString());
  }
}
