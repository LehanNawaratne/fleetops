package com.example.fleetops.driver.mapper;

import com.example.fleetops.driver.dto.DriverRequest;
import com.example.fleetops.driver.dto.DriverResponse;
import com.example.fleetops.driver.entity.Driver;
import com.example.fleetops.driver.enums.DriverStatus;

public final class DriverMapper {

    private DriverMapper() {
    }

    public static Driver toEntity(DriverRequest request) {
        if (request == null) {
            return null;
        }

        return Driver.builder()
                .licenseNumber(request.getLicenseNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .licenseExpiryDate(request.getLicenseExpiryDate())
                .status(DriverStatus.AVAILABLE)
                .build();
    }

    public static DriverResponse toResponse(Driver driver) {
        if (driver == null) {
            return null;
        }

        return DriverResponse.builder()
                .id(driver.getId())
                .licenseNumber(driver.getLicenseNumber())
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .phoneNumber(driver.getPhoneNumber())
                .licenseExpiryDate(driver.getLicenseExpiryDate())
                .status(driver.getStatus())
                .createdAt(driver.getCreatedAt())
                .updatedAt(driver.getUpdatedAt())
                .build();
    }

    public static void updateFromRequest(Driver driver, DriverRequest request) {
        if (driver == null || request == null) {
            return;
        }

        driver.setLicenseNumber(request.getLicenseNumber());
        driver.setFirstName(request.getFirstName());
        driver.setLastName(request.getLastName());
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setLicenseExpiryDate(request.getLicenseExpiryDate());
    }
}
