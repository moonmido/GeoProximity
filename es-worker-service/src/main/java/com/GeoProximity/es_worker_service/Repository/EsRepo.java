package com.GeoProximity.es_worker_service.Repository;

import com.GeoProximity.es_worker_service.Models.MyBusiness;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsRepo extends ElasticsearchRepository<MyBusiness,Long> {

    @Query("""
{
  "bool": {
    "filter": {
      "geo_distance": {
        "distance": "?2km",
        "location": {
          "lat": ?0,
          "lon": ?1
        }
      }
    }
  }
}
""")
    List<MyBusiness> findNearby(
            double lat,
            double lon,
            double distanceKm
    );




}
