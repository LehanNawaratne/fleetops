package com.example.fleetops.trip.dto;

import com.example.fleetops.trip.enums.TripStatus;
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
public class TripResponse {

    private Long id;
    private String origin;
    private String destination;
    private TripStatus status;
    private Long driverId;
    private String driverName;
    private Long vehicleId;
    private String vehiclePlateNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
