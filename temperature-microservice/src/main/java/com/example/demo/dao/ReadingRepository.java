package com.example.demo.dao;

import com.example.demo.domain.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadingRepository extends JpaRepository<Reading, Long> {
    Optional<Reading> findById(Long id);
}
