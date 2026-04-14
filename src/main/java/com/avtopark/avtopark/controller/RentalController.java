package com.avtopark.avtopark.controller;

import com.avtopark.avtopark.dto.RentalDTO;
import com.avtopark.avtopark.entity.Rental;
import com.avtopark.avtopark.mapper.RentalMapper;
import com.avtopark.avtopark.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    
    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private RentalMapper rentalMapper;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalDTOs);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id)
                .map(rentalMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<List<RentalDTO>> getRentalsByUserId(@PathVariable Long userId) {
        List<Rental> rentals = rentalService.findByUserId(userId);
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalDTOs);
    }
    
    @GetMapping("/car/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RentalDTO>> getRentalsByCarId(@PathVariable Long carId) {
        List<Rental> rentals = rentalService.findByCarId(carId);
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalDTOs);
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RentalDTO>> getRentalsByStatus(@PathVariable String status) {
        List<Rental> rentals = rentalService.findByRentalStatus(status);
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalDTOs);
    }
    
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RentalDTO>> getRentalsByDateRange(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        List<Rental> rentals = rentalService.findByStartDateBetween(startDate, endDate);
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalDTOs);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
        // Convert DTO to entity
        Rental rental = rentalMapper.toEntity(rentalDTO);
        // Set default status if not provided
        if (rental.getRentalStatus() == null || rental.getRentalStatus().isEmpty()) {
            rental.setRentalStatus("PENDING");
        }
        Rental savedRental = rentalService.saveRental(rental);
        RentalDTO savedRentalDTO = rentalMapper.toDto(savedRental);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRentalDTO);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable Long id, @RequestBody RentalDTO rentalDTO) {
        if (!rentalService.getRentalById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        rentalDTO.setId(id);
        Rental rental = rentalMapper.toEntity(rentalDTO);
        Rental updatedRental = rentalService.saveRental(rental);
        RentalDTO updatedRentalDTO = rentalMapper.toDto(updatedRental);
        return ResponseEntity.ok(updatedRentalDTO);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        if (!rentalService.getRentalById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }
}