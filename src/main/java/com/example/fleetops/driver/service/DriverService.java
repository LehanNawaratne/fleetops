package com.example.fleetops.driver.service;

import com.example.fleetops.common.exception.DuplicateResourceException;
import com.example.fleetops.common.exception.ResourceNotFoundException;
import com.example.fleetops.driver.dto.DriverRequest;
import com.example.fleetops.driver.dto.DriverResponse;
import com.example.fleetops.driver.entity.Driver;
import com.example.fleetops.driver.mapper.DriverMapper;
import com.example.fleetops.driver.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<DriverResponse> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(DriverMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DriverResponse createDriver(DriverRequest request) {
        validateDuplicateLicense(request.getLicenseNumber());

        Driver driver = DriverMapper.toEntity(request);
        Driver saved = driverRepository.save(driver);
        return DriverMapper.toResponse(saved);
    }

    public DriverResponse updateDriver(Long id, DriverRequest request) {
        Driver existing = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + id));

        if (!existing.getLicenseNumber().equals(request.getLicenseNumber())) {
            validateDuplicateLicense(request.getLicenseNumber());
        }

        DriverMapper.updateFromRequest(existing, request);
        Driver updated = driverRepository.save(existing);
        return DriverMapper.toResponse(updated);
    }

    private void validateDuplicateLicense(String licenseNumber) {
        driverRepository.findByLicenseNumber(licenseNumber)
                .ifPresent(driver -> {
                    throw new DuplicateResourceException("Driver with license number already exists: " + licenseNumber);
                });
    }
}
