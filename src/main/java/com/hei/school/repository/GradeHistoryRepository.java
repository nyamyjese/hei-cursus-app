package com.hei.school.repository;

import com.hei.school.model.GradeHistory;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeHistoryRepository extends JpaRepository<GradeHistory, UUID> {

  List<GradeHistory> findByGradeIdOrderByModifiedAtDesc(UUID gradeId);
}
