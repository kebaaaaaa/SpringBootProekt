package com.avtopark.avtopark.controller;

import com.avtopark.avtopark.dto.CarDTO;
import com.avtopark.avtopark.dto.CarDetailDTO;
import com.avtopark.avtopark.entity.Car;
import com.avtopark.avtopark.entity.CarDetail;
import com.avtopark.avtopark.mapper.CarMapper;
import com.avtopark.avtopark.service.CarService;
import com.avtopark.avtopark.service.CarDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    
    @Autowired
    private CarService carService;
    
    @Autowired
    private CarDetailService carDetailService;
    
    @Autowired
    private CarMapper carMapper;
    
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        List<CarDTO> carDTOs = cars.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(carMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<CarDTO>> getCarsByBrand(@PathVariable String brand) {
        List<Car> cars = carService.findByBrand(brand);
        List<CarDTO> carDTOs = cars.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }
    
    @GetMapping("/model/{model}")
    public ResponseEntity<List<CarDTO>> getCarsByModel(@PathVariable String model) {
        List<Car> cars = carService.findByModel(model);
        List<CarDTO> carDTOs = cars.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }
    
    @GetMapping("/year-range")
    public ResponseEntity<List<CarDTO>> getCarsByYearRange(
            @RequestParam int startYear, 
            @RequestParam int endYear) {
        List<Car> cars = carService.findByYearBetween(startYear, endYear);
        List<CarDTO> carDTOs = cars.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);
        Car savedCar = carService.saveCar(car);
        CarDTO savedCarDTO = carMapper.toDto(savedCar);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCarDTO);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long id, @RequestBody CarDTO carDTO) {
        if (!carService.getCarById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        carDTO.setId(id);
        Car car = carMapper.toEntity(carDTO);
        Car updatedCar = carService.saveCar(car);
        CarDTO updatedCarDTO = carMapper.toDto(updatedCar);
        return ResponseEntity.ok(updatedCarDTO);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        if (!carService.getCarById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}