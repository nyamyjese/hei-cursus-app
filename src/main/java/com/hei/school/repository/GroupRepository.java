package com.hei.school.repository;

import com.hei.school.model.Group;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, UUID> {

  List<Group> findByPromotionId(UUID promotionId);

  List<Group> findByTrackId(UUID trackId);

  List<Group> findByPromotionIdAndTrackId(UUID promotionId, UUID trackId);
}
