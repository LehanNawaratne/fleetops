package com.example.fleetops.driver.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRequest {

    @NotBlank(message = "License number is required")
    @Size(max = 64, message = "License number must be 64 characters or fewer")
    private String licenseNumber;

    @NotBlank(message = "First name is required")
    @Size(max = 128, message = "First name must be 128 characters or fewer")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 128, message = "Last name must be 128 characters or fewer")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9\\- ]{7,20}$", message = "Phone number is invalid")
    private String phoneNumber;

    @FutureOrPresent(message = "License expiry date must be today or in the future")
    private LocalDate licenseExpiryDate;
}
