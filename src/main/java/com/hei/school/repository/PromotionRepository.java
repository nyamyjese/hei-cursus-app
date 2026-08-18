package com.hei.school.repository;

import com.hei.school.model.Promotion;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, UUID> {}
