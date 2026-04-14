package com.avtopark.avtopark.controller;

import com.avtopark.avtopark.dto.ServiceDTO;
import com.avtopark.avtopark.entity.Service;
import com.avtopark.avtopark.mapper.ServiceMapper;
import com.avtopark.avtopark.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    
    @Autowired
    private ServiceService serviceService;
    
    @Autowired
    private ServiceMapper serviceMapper;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<Service> services = serviceService.getAllServices();
        List<ServiceDTO> serviceDTOs = services.stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDTOs);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id)
                .map(serviceMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDTO> getServiceByName(@PathVariable String name) {
        Service service = serviceService.findByName(name);
        if (service == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(serviceMapper.toDto(service));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO) {
        Service service = serviceMapper.toEntity(serviceDTO);
        Service savedService = serviceService.saveService(service);
        ServiceDTO savedServiceDTO = serviceMapper.toDto(savedService);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedServiceDTO);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable Long id, @RequestBody ServiceDTO serviceDTO) {
        if (!serviceService.getServiceById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        serviceDTO.setId(id);
        Service service = serviceMapper.toEntity(serviceDTO);
        Service updatedService = serviceService.saveService(service);
        ServiceDTO updatedServiceDTO = serviceMapper.toDto(updatedService);
        return ResponseEntity.ok(updatedServiceDTO);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        if (!serviceService.getServiceById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}