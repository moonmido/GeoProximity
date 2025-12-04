package com.GeoProximity.business_service.Controllers;

import com.GeoProximity.business_service.Models.MyBusiness;
import com.GeoProximity.business_service.Services.BusinesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private final BusinesService service;

    public BusinessController(BusinesService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBusiness(@RequestBody MyBusiness business) {
        service.addBusiness(business);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Business created successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBusiness(@RequestBody MyBusiness business) {
        service.updateBusiness(business);
        return ResponseEntity.ok("Business updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBusiness(@PathVariable int id) {
        service.removeBusiness(id);
        return ResponseEntity.ok("Business deleted successfully");
    }
}
