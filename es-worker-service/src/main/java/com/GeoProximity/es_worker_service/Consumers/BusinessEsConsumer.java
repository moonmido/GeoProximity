package com.GeoProximity.es_worker_service.Consumers;

import com.GeoProximity.es_worker_service.Events.BusinessEvent;
import com.GeoProximity.es_worker_service.Models.MyBusiness;
import com.GeoProximity.es_worker_service.Repository.EsRepo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BusinessEsConsumer {

    private final EsRepo repo;


    public BusinessEsConsumer(EsRepo repo) {
        this.repo = repo;
    }

    @KafkaListener(groupId = "business-group", topics = "business-events")
    public void consume(BusinessEvent businessEvent) {
        MyBusiness document = toDocument(businessEvent);
        if (businessEvent.getEventType().equals(BusinessEvent.EventType.CREATED) || businessEvent.getEventType().equals(BusinessEvent.EventType.UPDATED)) {
            repo.save(document);
        } else if (businessEvent.getEventType().equals(BusinessEvent.EventType.DELETED)) {
            repo.deleteById(businessEvent.getBusinessId());
        }
    }

    private MyBusiness toDocument(BusinessEvent event) {

        if (event == null) {
            throw new IllegalArgumentException("BusinessEvent cannot be null");
        }

        MyBusiness doc = new MyBusiness();
        doc.setBusinessId(event.getBusinessId());
        doc.setAddress(event.getAddress());
        doc.setCity(event.getCity());
        doc.setCountry(event.getCountry());
        doc.setState(event.getState());
        doc.setLocation(event.getLatitude(), event.getLongitude());

        return doc;
    }
}