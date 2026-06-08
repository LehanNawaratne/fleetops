package com.example.fleetops.vehicle.dto;

import com.example.fleetops.trip.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {

    private Long id;
    private String plateNumber;
    private String manufacturer;
    private String model;
    private VehicleStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
