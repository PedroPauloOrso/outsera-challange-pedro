package com.pedro.orso.outsera.repository;

import com.pedro.orso.outsera.domain.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {
    Optional<Studio> findByName(String name);
}
