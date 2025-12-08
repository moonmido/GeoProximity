package com.GeoProximity.business_service.Services;

import com.GeoProximity.business_service.Events.BusinessEvent;
import com.GeoProximity.business_service.Models.MyBusiness;
import com.GeoProximity.business_service.Repository.MyBusinessRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class BusinessService {

    private final MyBusinessRepository repository;
    private final RedisTemplate<String,Object> redisTemplate;
    private final KafkaTemplate<String,BusinessEvent> kafkaTemplate;

    private static final String BUSINESS_KEY = "business:";
    private static final String BUSINESS_ALL_KEY = "business:all";
    private static final Duration CACHE_TTL = Duration.ofMinutes(10);


    public BusinessService(MyBusinessRepository repository, RedisTemplate<String, Object> redisTemplate, KafkaTemplate<String, BusinessEvent> kafkaTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    //Create A new Business

    public MyBusiness createBusiness(MyBusiness myBusiness) {

        if (myBusiness == null)
            throw new IllegalArgumentException("Invalid input");

        MyBusiness saved = repository.save(myBusiness);

        redisTemplate.opsForValue()
                .set(BUSINESS_KEY + saved.getBusiness_id(), saved, CACHE_TTL);
        redisTemplate.delete(BUSINESS_ALL_KEY);

        kafkaTemplate.send(
                "business-events",
                toEvent(saved, BusinessEvent.EventType.CREATED)
        );

        return saved;
    }



    //Delete An exist Business
    public void removeBusiness(long id) {

        if (id <= 0)
            throw new IllegalArgumentException("Invalid id");

        MyBusiness business = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Business not found"));

        repository.deleteById(id);

        redisTemplate.delete(BUSINESS_KEY + id);
        redisTemplate.delete(BUSINESS_ALL_KEY);

        BusinessEvent event = new BusinessEvent();
        event.setBusinessId(id);
        event.setEventType(BusinessEvent.EventType.DELETED);

        kafkaTemplate.send("business-events", event);
    }



    //Update An exist Business
    public MyBusiness updateBusiness(long id, MyBusiness updated) {

        if (id <= 0 || updated == null)
            throw new IllegalArgumentException("Invalid data");

        MyBusiness existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Business not found"));

        existing.setAddress(updated.getAddress());
        existing.setCity(updated.getCity());
        existing.setCountry(updated.getCountry());
        existing.setLatitude(updated.getLatitude());
        existing.setLongtitude(updated.getLongtitude());
        existing.setState(updated.getState());

        MyBusiness saved = repository.save(existing);

        redisTemplate.opsForValue()
                .set(BUSINESS_KEY + id, saved, CACHE_TTL);
        redisTemplate.delete(BUSINESS_ALL_KEY);

        kafkaTemplate.send(
                "business-events",
                toEvent(saved, BusinessEvent.EventType.UPDATED)
        );

        return saved;
    }


    //Get Business By id

    public MyBusiness getBusinessById(long id) {
        if (id <= 0)
            throw new IllegalArgumentException("Invalid id");
        String key = BUSINESS_KEY + id;
        MyBusiness cached = (MyBusiness) redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return cached;
        }
        MyBusiness business = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Business not found"));

        redisTemplate.opsForValue()
                .set(key, business, CACHE_TTL);
        return business;
    }

    @SuppressWarnings("unchecked")
    public List<MyBusiness> getAllBusinesses() {

        List<MyBusiness> cached =
                (List<MyBusiness>) redisTemplate.opsForValue()
                        .get(BUSINESS_ALL_KEY);

        if (cached != null) {
            return cached;
        }

        List<MyBusiness> businesses = repository.findAll();

        redisTemplate.opsForValue()
                .set(BUSINESS_ALL_KEY, businesses, Duration.ofMinutes(5));

        return businesses;
    }

    private BusinessEvent toEvent(MyBusiness business, BusinessEvent.EventType type) {
        BusinessEvent event = new BusinessEvent();
        event.setBusinessId(business.getBusiness_id());
        event.setAddress(business.getAddress());
        event.setCity(business.getCity());
        event.setCountry(business.getCountry());
        event.setState(business.getState());
        event.setLatitude(business.getLatitude());
        event.setLongitude(business.getLongtitude());
        event.setEventType(type);
        return event;
    }


}
