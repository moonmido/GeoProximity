package com.GeoProximity.es_worker_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EsWorkerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsWorkerServiceApplication.class, args);
	}

}
