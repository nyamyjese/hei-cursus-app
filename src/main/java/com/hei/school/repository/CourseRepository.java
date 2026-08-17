package com.hei.school.repository;

import com.hei.school.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findByTrackId (UUID trackId);

    List<Course> findByTrackIdAndSemester (UUID trackId, int semester);
}
