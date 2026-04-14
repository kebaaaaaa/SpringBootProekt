package com.avtopark.avtopark.service;

import com.avtopark.avtopark.entity.Car;
import com.avtopark.avtopark.entity.CarImage;
import com.avtopark.avtopark.repository.CarImageRepository;
import com.avtopark.avtopark.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarImageService {
    
    private final CarImageRepository carImageRepository;
    private final CarRepository carRepository;
    
    public CarImageService(CarImageRepository carImageRepository, CarRepository carRepository) {
        this.carImageRepository = carImageRepository;
        this.carRepository = carRepository;
    }
    
    public List<CarImage> getAllCarImages() {
        return carImageRepository.findAll();
    }
    
    public Optional<CarImage> getCarImageById(Long id) {
        return carImageRepository.findById(id);
    }
    
    public List<CarImage> getCarImagesByCarId(Long carId) {
        return carImageRepository.findByCarId(carId);
    }
    
    public CarImage saveCarImage(CarImage carImage) {
        return carImageRepository.save(carImage);
    }
    
    public void deleteCarImage(Long id) {
        carImageRepository.deleteById(id);
    }
    
    public CarImage createCarImage(Long carId, String fileName, String fileType, byte[] fileContent) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + carId));
        
        CarImage carImage = new CarImage();
        carImage.setCar(car);
        carImage.setFileName(fileName);
        carImage.setFileType(fileType);
        carImage.setFileContent(fileContent);
        
        return carImageRepository.save(carImage);
    }
}