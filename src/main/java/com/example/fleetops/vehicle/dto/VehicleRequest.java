package com.example.fleetops.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequest {

    @NotBlank(message = "Plate number is required")
    @Size(max = 64, message = "Plate number must be 64 characters or fewer")
    private String plateNumber;

    @NotBlank(message = "Manufacturer is required")
    @Size(max = 128, message = "Manufacturer must be 128 characters or fewer")
    private String manufacturer;

    @NotBlank(message = "Model is required")
    @Size(max = 128, message = "Model must be 128 characters or fewer")
    private String model;
}
