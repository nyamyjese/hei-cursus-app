package com.hei.school.repository;

import com.hei.school.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TrackRepository extends JpaRepository<Track, UUID> {

    Optional<Track> findByCode  (String code);
}
