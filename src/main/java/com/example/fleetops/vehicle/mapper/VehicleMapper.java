package com.example.fleetops.vehicle.mapper;

import com.example.fleetops.trip.enums.VehicleStatus;
import com.example.fleetops.vehicle.dto.VehicleRequest;
import com.example.fleetops.vehicle.dto.VehicleResponse;
import com.example.fleetops.vehicle.entity.Vehicle;

public final class VehicleMapper {

    private VehicleMapper() {
    }

    public static VehicleResponse toResponse(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        return VehicleResponse.builder()
                .id(vehicle.getId())
                .plateNumber(vehicle.getPlateNumber())
                .manufacturer(vehicle.getManufacturer())
                .model(vehicle.getModel())
                .status(vehicle.getStatus())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }

    public static Vehicle toEntity(VehicleRequest request) {
        if (request == null) {
            return null;
        }

        return Vehicle.builder()
                .plateNumber(request.getPlateNumber())
                .manufacturer(request.getManufacturer())
                .model(request.getModel())
                .status(VehicleStatus.AVAILABLE)
                .build();
    }

    public static void updateFromRequest(Vehicle vehicle, VehicleRequest request) {
        if (vehicle == null || request == null) {
            return;
        }

        vehicle.setPlateNumber(request.getPlateNumber());
        vehicle.setManufacturer(request.getManufacturer());
        vehicle.setModel(request.getModel());
    }
}
