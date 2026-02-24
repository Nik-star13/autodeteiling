package com.autodet.autodet.repository;

import com.autodet.autodet.model.CarService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarServiceRepository extends JpaRepository<CarService, Long> {
    Optional<CarService> findByName(String name);
}
