package com.example.fleetops.trip.dto;

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
public class TripCreateRequest {

    @NotBlank(message = "Origin is required")
    @Size(max = 255, message = "Origin must be 255 characters or fewer")
    private String origin;

    @NotBlank(message = "Destination is required")
    @Size(max = 255, message = "Destination must be 255 characters or fewer")
    private String destination;
}
