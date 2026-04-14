package com.avtopark.avtopark.service;

import com.avtopark.avtopark.entity.Car;
import com.avtopark.avtopark.entity.Rental;
import com.avtopark.avtopark.entity.User;
import com.avtopark.avtopark.repository.CarRepository;
import com.avtopark.avtopark.repository.RentalRepository;
import com.avtopark.avtopark.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RentalService {
    
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    
    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, CarRepository carRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }
    
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }
    
    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }
    
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }
    
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }
    
    public List<Rental> findByUserId(Long userId) {
        return rentalRepository.findByUserId(userId);
    }
    
    public List<Rental> findByCarId(Long carId) {
        return rentalRepository.findByCarId(carId);
    }
    
    public List<Rental> findByRentalStatus(String status) {
        return rentalRepository.findByRentalStatus(status);
    }
    
    public List<Rental> findByStartDateBetween(LocalDate start, LocalDate end) {
        return rentalRepository.findByStartDateBetween(start, end);
    }
    
    public List<Rental> findByEndDateBetween(LocalDate start, LocalDate end) {
        return rentalRepository.findByEndDateBetween(start, end);
    }
    
    public Rental createRental(Long userId, Long carId, LocalDate startDate, LocalDate endDate, BigDecimal price) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + carId));
        
        Rental rental = new Rental();
        rental.setUser(user);
        rental.setCar(car);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
        rental.setPrice(price);
        rental.setRentalStatus("PENDING"); // Default status
        
        return rentalRepository.save(rental);
    }
}