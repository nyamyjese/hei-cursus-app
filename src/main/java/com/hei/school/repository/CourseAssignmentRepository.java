package com.hei.school.repository;

import com.hei.school.model.CourseAssignment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, UUID> {

  List<CourseAssignment> findByCourseId(UUID courseId);

  List<CourseAssignment> findByTeacherId(UUID teacherId);

  boolean existsByTeacherIdAndCourseId(UUID teacherId, UUID courseId);

  boolean existsByTeacherIdAndCourseIdAndGroupId(UUID teacherId, UUID courseId, UUID groupId);
}
