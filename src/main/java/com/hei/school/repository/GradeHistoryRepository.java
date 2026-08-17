package com.hei.school.repository;

import com.hei.school.model.GradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GradeHistoryRepository extends JpaRepository<GradeHistory, UUID> {

    List<GradeHistory> findByGradeIdOrderByModifiedAtDesc(UUID gradeId);
}
