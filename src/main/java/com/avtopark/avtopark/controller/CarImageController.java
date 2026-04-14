package com.avtopark.avtopark.controller;

import com.avtopark.avtopark.dto.CarImageDTO;
import com.avtopark.avtopark.entity.CarImage;
import com.avtopark.avtopark.mapper.CarImageMapper;
import com.avtopark.avtopark.service.CarImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/car-images")
public class CarImageController {
    
    @Autowired
    private CarImageService carImageService;
    
    @Autowired
    private CarImageMapper carImageMapper;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CarImageDTO>> getAllCarImages() {
        List<CarImage> carImages = carImageService.getAllCarImages();
        List<CarImageDTO> carImageDTOs = carImages.stream()
                .map(carImageMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carImageDTOs);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarImageDTO> getCarImageById(@PathVariable Long id) {
        return carImageService.getCarImageById(id)
                .map(carImageMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/car/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CarImageDTO>> getCarImagesByCarId(@PathVariable Long carId) {
        List<CarImage> carImages = carImageService.getCarImagesByCarId(carId);
        List<CarImageDTO> carImageDTOs = carImages.stream()
                .map(carImageMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carImageDTOs);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarImageDTO> createCarImage(@RequestBody CarImageDTO carImageDTO) {
        CarImage carImage = carImageMapper.toEntity(carImageDTO);
        CarImage savedCarImage = carImageService.saveCarImage(carImage);
        CarImageDTO savedCarImageDTO = carImageMapper.toDto(savedCarImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCarImageDTO);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarImageDTO> updateCarImage(@PathVariable Long id, @RequestBody CarImageDTO carImageDTO) {
        if (!carImageService.getCarImageById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        carImageDTO.setId(id);
        CarImage carImage = carImageMapper.toEntity(carImageDTO);
        CarImage updatedCarImage = carImageService.saveCarImage(carImage);
        CarImageDTO updatedCarImageDTO = carImageMapper.toDto(updatedCarImage);
        return ResponseEntity.ok(updatedCarImageDTO);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCarImage(@PathVariable Long id) {
        if (!carImageService.getCarImageById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        carImageService.deleteCarImage(id);
        return ResponseEntity.noContent().build();
    }
}