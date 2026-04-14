package com.avtopark.avtopark.repository;

import com.avtopark.avtopark.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserId(Long userId);
    List<Rental> findByCarId(Long carId);
    List<Rental> findByRentalStatus(String status);
    List<Rental> findByStartDateBetween(LocalDate start, LocalDate end);
    List<Rental> findByEndDateBetween(LocalDate start, LocalDate end);
}