package com.avtopark.avtopark.service;

import com.avtopark.avtopark.repository.ServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceService {
    
    private final ServiceRepository serviceRepository;
    
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }
    
    public List<com.avtopark.avtopark.entity.Service> getAllServices() {
        return serviceRepository.findAll();
    }
    
    public Optional<com.avtopark.avtopark.entity.Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }
    
    public com.avtopark.avtopark.entity.Service saveService(com.avtopark.avtopark.entity.Service service) {
        return serviceRepository.save(service);
    }
    
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
    
    public com.avtopark.avtopark.entity.Service findByName(String name) {
        return serviceRepository.findByName(name);
    }
}