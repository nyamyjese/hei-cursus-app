package com.hei.school.repository;

import com.hei.school.model.Course;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, UUID> {

  List<Course> findByTrackId(UUID trackId);

  List<Course> findByTrackIdAndSemester(UUID trackId, int semester);
}
