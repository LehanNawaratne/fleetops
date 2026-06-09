package com.example.fleetops.trip.service;

import com.example.fleetops.common.exception.InvalidResourceStateException;
import com.example.fleetops.common.exception.ResourceNotFoundException;
import com.example.fleetops.driver.entity.Driver;
import com.example.fleetops.driver.enums.DriverStatus;
import com.example.fleetops.driver.repository.DriverRepository;
import com.example.fleetops.trip.dto.TripAssignRequest;
import com.example.fleetops.trip.dto.TripCreateRequest;
import com.example.fleetops.trip.dto.TripResponse;
import com.example.fleetops.trip.entity.Trip;
import com.example.fleetops.trip.enums.TripStatus;
import com.example.fleetops.trip.mapper.TripMapper;
import com.example.fleetops.trip.repository.TripRepository;
import com.example.fleetops.vehicle.entity.Vehicle;
import com.example.fleetops.vehicle.enums.VehicleStatus;
import com.example.fleetops.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripService {

    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    public TripService(TripRepository tripRepository,
                       DriverRepository driverRepository,
                       VehicleRepository vehicleRepository) {
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<TripResponse> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(TripMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TripResponse getTripById(Long id) {
        Trip trip = findTripById(id);
        return TripMapper.toResponse(trip);
    }

    public TripResponse createTrip(TripCreateRequest request) {
        validateTripRequest(request);

        Trip trip = Trip.builder()
                .origin(request.getOrigin().trim())
                .destination(request.getDestination().trim())
                .status(TripStatus.CREATED)
                .build();

        Trip saved = tripRepository.save(trip);
        return TripMapper.toResponse(saved);
    }

    public TripResponse assignTrip(Long id, TripAssignRequest request) {
        Trip trip = findTripById(id);
        validateTripAssignmentRequest(trip, request);

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + request.getDriverId()));
        if (driver.getStatus() != DriverStatus.AVAILABLE) {
            throw new InvalidResourceStateException("Driver is unavailable for assignment");
        }

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + request.getVehicleId()));
        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new InvalidResourceStateException("Vehicle is unavailable for assignment");
        }

        trip.setDriver(driver);
        trip.setVehicle(vehicle);
        trip.setStatus(TripStatus.ASSIGNED);

        driver.setStatus(DriverStatus.ASSIGNED);
        vehicle.setStatus(VehicleStatus.ASSIGNED);

        Trip updated = tripRepository.save(trip);
        return TripMapper.toResponse(updated);
    }

    private Trip findTripById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + id));
    }

    private void validateTripRequest(TripCreateRequest request) {
        if (request == null) {
            throw new InvalidResourceStateException("Trip request cannot be null");
        }
        if (request.getOrigin() == null || request.getOrigin().trim().isEmpty()) {
            throw new InvalidResourceStateException("Origin is required");
        }
        if (request.getDestination() == null || request.getDestination().trim().isEmpty()) {
            throw new InvalidResourceStateException("Destination is required");
        }
        if (request.getOrigin().trim().equalsIgnoreCase(request.getDestination().trim())) {
            throw new InvalidResourceStateException("Origin and destination cannot be the same");
        }
    }

    private void validateTripAssignmentRequest(Trip trip, TripAssignRequest request) {
        if (trip.getStatus() != TripStatus.CREATED) {
            throw new InvalidResourceStateException("Only trips with status CREATED can be assigned");
        }
        if (request == null) {
            throw new InvalidResourceStateException("Assignment request cannot be null");
        }
        if (request.getDriverId() == null) {
            throw new InvalidResourceStateException("Driver ID is required for assignment");
        }
        if (request.getVehicleId() == null) {
            throw new InvalidResourceStateException("Vehicle ID is required for assignment");
        }
    }
}
