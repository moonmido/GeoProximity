package com.GeoProximity.es_worker_service.Controllers;

import com.GeoProximity.es_worker_service.Models.MyBusiness;
import com.GeoProximity.es_worker_service.Services.BusinessEsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses")
public class BusinessEsController {

    private final BusinessEsService businessEsService;

    public BusinessEsController(BusinessEsService businessEsService) {
        this.businessEsService = businessEsService;
    }


    @GetMapping("/nearby")
    public ResponseEntity<List<MyBusiness>> getNearbyBusinesses(
            @RequestParam("lat") double latitude,
            @RequestParam("lon") double longitude,
            @RequestParam("radiusKm") double radiusKm
    ) {
        List<MyBusiness> businesses =
                businessEsService.getNearbyBusinesses(latitude, longitude, radiusKm);
        return ResponseEntity.ok(businesses);
    }
}

