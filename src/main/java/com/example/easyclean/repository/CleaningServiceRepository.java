package com.example.easyclean.repository;

import com.example.easyclean.model.CleaningService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CleaningServiceRepository extends JpaRepository<CleaningService,Long> {
   // Optional<CleaningService> findCleaningServiceByName(String serviceName);
}
