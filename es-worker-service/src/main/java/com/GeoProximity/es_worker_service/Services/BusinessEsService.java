package com.GeoProximity.es_worker_service.Services;

import com.GeoProximity.es_worker_service.Models.MyBusiness;
import com.GeoProximity.es_worker_service.Repository.EsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessEsService {

    private final EsRepo repo;

    public BusinessEsService(EsRepo repo) {
        this.repo = repo;
    }

    public List<MyBusiness> getNearbyBusinesses(
            double latitude,
            double longitude,
            double radiusKm
    ) {
        if (radiusKm <= 0) {
            throw new IllegalArgumentException("Radius must be > 0");
        }

        return repo.findNearby(latitude, longitude, radiusKm);
    }
}

