package com.avtopark.avtopark.controller;

import com.avtopark.avtopark.dto.CarDetailDTO;
import com.avtopark.avtopark.entity.CarDetail;
import com.avtopark.avtopark.mapper.CarMapper;
import com.avtopark.avtopark.service.CarDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/car-details")
public class CarDetailController {
    
    @Autowired
    private CarDetailService carDetailService;
    
    @Autowired
    private CarMapper carMapper;
    
    @GetMapping
    public ResponseEntity<List<CarDetailDTO>> getAllCarDetails() {
        List<CarDetail> carDetails = carDetailService.getAllCarDetails();
        List<CarDetailDTO> carDetailDTOs = carDetails.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDetailDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CarDetailDTO> getCarDetailById(@PathVariable Long id) {
        return carDetailService.getCarDetailById(id)
                .map(carMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDetailDTO> createCarDetail(@RequestBody CarDetailDTO carDetailDTO) {
        CarDetail carDetail = carMapper.toEntity(carDetailDTO);
        CarDetail savedCarDetail = carDetailService.saveCarDetail(carDetail);
        CarDetailDTO savedCarDetailDTO = carMapper.toDto(savedCarDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCarDetailDTO);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDetailDTO> updateCarDetail(@PathVariable Long id, @RequestBody CarDetailDTO carDetailDTO) {
        if (!carDetailService.getCarDetailById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        carDetailDTO.setId(id);
        CarDetail carDetail = carMapper.toEntity(carDetailDTO);
        CarDetail updatedCarDetail = carDetailService.saveCarDetail(carDetail);
        CarDetailDTO updatedCarDetailDTO = carMapper.toDto(updatedCarDetail);
        return ResponseEntity.ok(updatedCarDetailDTO);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCarDetail(@PathVariable Long id) {
        if (!carDetailService.getCarDetailById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        carDetailService.deleteCarDetail(id);
        return ResponseEntity.noContent().build();
    }
}