package com.hei.school.repository;

import com.hei.school.model.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, UUID> {

    Optional<GroupMembership> findByStudentIdAndEndDateIsNull(UUID studentId);

    List<GroupMembership> findByStudentIrdOrderByStartDateDesc(UUID studentId);

}
