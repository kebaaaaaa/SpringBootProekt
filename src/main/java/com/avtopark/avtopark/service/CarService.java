package com.avtopark.avtopark.service;

import com.avtopark.avtopark.entity.Car;
import com.avtopark.avtopark.entity.CarDetail;
import com.avtopark.avtopark.repository.CarRepository;
import com.avtopark.avtopark.repository.CarDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarService {
    
    private final CarRepository carRepository;
    private final CarDetailRepository carDetailRepository;
    
    public CarService(CarRepository carRepository, CarDetailRepository carDetailRepository) {
        this.carRepository = carRepository;
        this.carDetailRepository = carDetailRepository;
    }
    
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }
    
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }
    
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
    
    public List<Car> findByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }
    
    public List<Car> findByModel(String model) {
        return carRepository.findByModel(model);
    }
    
    public List<Car> findByYearBetween(int startYear, int endYear) {
        return carRepository.findByYearBetween(startYear, endYear);
    }
}