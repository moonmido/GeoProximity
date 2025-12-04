package com.GeoProximity.business_service.Repository;

import com.GeoProximity.business_service.Models.MyBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBusinessRepo extends JpaRepository<MyBusiness,Integer> {
}
