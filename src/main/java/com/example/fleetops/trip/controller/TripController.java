package com.example.fleetops.trip.controller;

import com.example.fleetops.trip.dto.TripAssignRequest;
import com.example.fleetops.trip.dto.TripCreateRequest;
import com.example.fleetops.trip.dto.TripResponse;
import com.example.fleetops.trip.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> getTripById(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getTripById(id));
    }

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@Valid @RequestBody TripCreateRequest request) {
        TripResponse response = tripService.createTrip(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<TripResponse> assignTrip(@PathVariable Long id,
                                                   @Valid @RequestBody TripAssignRequest request) {
        return ResponseEntity.ok(tripService.assignTrip(id, request));
    }
}
