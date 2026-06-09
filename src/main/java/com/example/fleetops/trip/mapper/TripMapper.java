package com.example.fleetops.trip.mapper;

import com.example.fleetops.trip.dto.TripResponse;
import com.example.fleetops.trip.entity.Trip;

public final class TripMapper {

    private TripMapper() {
    }

    public static TripResponse toResponse(Trip trip) {
        if (trip == null) {
            return null;
        }

        Long driverId = null;
        String driverName = null;
        if (trip.getDriver() != null) {
            driverId = trip.getDriver().getId();
            driverName = String.format("%s %s", trip.getDriver().getFirstName(), trip.getDriver().getLastName());
        }

        Long vehicleId = null;
        String vehiclePlateNumber = null;
        if (trip.getVehicle() != null) {
            vehicleId = trip.getVehicle().getId();
            vehiclePlateNumber = trip.getVehicle().getPlateNumber();
        }

        return TripResponse.builder()
                .id(trip.getId())
                .origin(trip.getOrigin())
                .destination(trip.getDestination())
                .status(trip.getStatus())
                .driverId(driverId)
                .driverName(driverName)
                .vehicleId(vehicleId)
                .vehiclePlateNumber(vehiclePlateNumber)
                .createdAt(trip.getCreatedAt())
                .updatedAt(trip.getUpdatedAt())
                .build();
    }
}
