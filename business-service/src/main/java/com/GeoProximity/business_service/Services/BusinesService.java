package com.GeoProximity.business_service.Services;

import com.GeoProximity.business_service.Models.MyBusiness;
import com.GeoProximity.business_service.Repository.MyBusinessRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BusinesService {

    private static final Logger Log = LoggerFactory.getLogger(BusinesService.class);
    private final MyBusinessRepo repo;

    public BusinesService(MyBusinessRepo repo) {
        this.repo = repo;
    }

    public void addBusiness(MyBusiness myBusiness) {
        if (myBusiness == null) {
            throw new IllegalStateException("Business object is null!");
        }

        repo.save(myBusiness);
        Log.info("Business added");
    }

    public void updateBusiness(MyBusiness updatedBusiness) {
        if (updatedBusiness == null || updatedBusiness.getId() < 0) {
            throw new IllegalStateException("Business ID is required for update!");
        }

        if (!repo.existsById(updatedBusiness.getId())) {
            throw new IllegalStateException("Business with ID " + updatedBusiness.getId() + " not found!");
        }

        repo.save(updatedBusiness);
        Log.info("Business updated");
    }

    public void removeBusiness(int id) {
        if (!repo.existsById(id)) {
            throw new IllegalStateException("No business found with ID " + id);
        }

        repo.deleteById(id);
        Log.info("Business removed");
    }
}
