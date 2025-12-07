package com.GeoProximity.business_service.Controllers;

import java.util.Map;
import com.GeoProximity.business_service.Models.MyBusiness;
import com.GeoProximity.business_service.Services.BusinessService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(
        value = "/api/businesses",
        consumes = "application/json",
        produces = "application/json"
)
public class BusinessController {

    private final BusinessService service;

    public BusinessController(BusinessService service) {
        this.service = service;
    }

    // ---------------- CREATE ----------------
    @PostMapping
    public ResponseEntity<MyBusiness> create(@RequestBody MyBusiness business) {
        MyBusiness created = service.createBusiness(business);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------- GET BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<MyBusiness> getById(@PathVariable long id) {
        MyBusiness business = service.getBusinessById(id);
        return ResponseEntity.ok(business);
    }

    // ---------------- GET ALL ----------------
    @GetMapping
    public ResponseEntity<List<MyBusiness>> getAll() {
        return ResponseEntity.ok(service.getAllBusinesses());
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<MyBusiness> update(
            @PathVariable long id,
            @RequestBody MyBusiness business) {

        MyBusiness updated = service.updateBusiness(id, business);
        return ResponseEntity.ok(updated);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.removeBusiness(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------- LOCAL EXCEPTION HANDLING ----------------

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException e) {
        return error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(
            org.springframework.web.server.ResponseStatusException e) {

        return error((HttpStatus) e.getStatusCode(), e.getReason());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception e) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    // ---------------- ERROR BODY ----------------
    private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}

