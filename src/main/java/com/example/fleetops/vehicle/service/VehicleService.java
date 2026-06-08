package com.example.fleetops.vehicle.service;

import com.example.fleetops.common.exception.DuplicateResourceException;
import com.example.fleetops.common.exception.InvalidResourceStateException;
import com.example.fleetops.common.exception.ResourceNotFoundException;
import com.example.fleetops.vehicle.dto.VehicleRequest;
import com.example.fleetops.vehicle.dto.VehicleResponse;
import com.example.fleetops.vehicle.entity.Vehicle;
import com.example.fleetops.vehicle.mapper.VehicleMapper;
import com.example.fleetops.vehicle.repository.VehicleRepository;
import com.example.fleetops.trip.enums.VehicleStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(VehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    public VehicleResponse createVehicle(VehicleRequest request) {
        validateDuplicatePlate(request.getPlateNumber());

        Vehicle vehicle = VehicleMapper.toEntity(request);
        vehicle.setStatus(VehicleStatus.AVAILABLE);

        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleMapper.toResponse(saved);
    }

    public VehicleResponse updateVehicle(Long id, VehicleRequest request) {
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        if (existing.getStatus() == VehicleStatus.ASSIGNED) {
            throw new InvalidResourceStateException("Vehicle is currently assigned and cannot be updated");
        }
        if (existing.getStatus() == VehicleStatus.MAINTENANCE) {
            throw new InvalidResourceStateException("Vehicle is under maintenance and cannot be updated");
        }

        if (!existing.getPlateNumber().equals(request.getPlateNumber())) {
            validateDuplicatePlate(request.getPlateNumber());
        }

        VehicleMapper.updateFromRequest(existing, request);
        Vehicle updated = vehicleRepository.save(existing);
        return VehicleMapper.toResponse(updated);
    }

    public void deleteVehicle(Long id) {
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        if (existing.getStatus() == VehicleStatus.ASSIGNED) {
            throw new InvalidResourceStateException("Vehicle is currently assigned and cannot be deleted");
        }
        if (existing.getStatus() == VehicleStatus.MAINTENANCE) {
            throw new InvalidResourceStateException("Vehicle is under maintenance and cannot be deleted");
        }

        vehicleRepository.delete(existing);
    }

    private void validateDuplicatePlate(String plateNumber) {
        vehicleRepository.findByPlateNumber(plateNumber)
                .ifPresent(vehicle -> {
                    throw new DuplicateResourceException("Vehicle with plate number already exists: " + plateNumber);
                });
    }
}
