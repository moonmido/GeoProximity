package com.GeoProximity.business_service.Services;

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
    private final KafkaTemplate<String,Object> kafkaTemplate;

    private static final String BUSINESS_KEY = "business:";
    private static final String BUSINESS_ALL_KEY = "business:all";
    private static final Duration CACHE_TTL = Duration.ofMinutes(10);


    public BusinessService(MyBusinessRepository repository, RedisTemplate<String, Object> redisTemplate, KafkaTemplate<String, Object> kafkaTemplate) {
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
        kafkaTemplate.send("business_created", saved);
        return saved;

    }


    //Delete An exist Business
    public void removeBusiness(long id) {
        if (id <= 0)
            throw new IllegalArgumentException("Invalid id");

        if (!repository.existsById(id))
            throw new NotFoundException("Business not found");

        repository.deleteById(id);
        redisTemplate.delete(BUSINESS_KEY + id);
        redisTemplate.delete(BUSINESS_ALL_KEY);

        kafkaTemplate.send("business_deleted", id);
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

        kafkaTemplate.send("business_updated", saved);

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


}
