package com.avtopark.avtopark.service;

import com.avtopark.avtopark.entity.CarDetail;
import com.avtopark.avtopark.repository.CarDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarDetailService {
    
    private final CarDetailRepository carDetailRepository;
    
    public CarDetailService(CarDetailRepository carDetailRepository) {
        this.carDetailRepository = carDetailRepository;
    }
    
    public List<CarDetail> getAllCarDetails() {
        return carDetailRepository.findAll();
    }
    
    public Optional<CarDetail> getCarDetailById(Long id) {
        return carDetailRepository.findById(id);
    }
    
    public CarDetail saveCarDetail(CarDetail carDetail) {
        return carDetailRepository.save(carDetail);
    }
    
    public void deleteCarDetail(Long id) {
        carDetailRepository.deleteById(id);
    }
}