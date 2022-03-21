package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.RequestRegistration;

public interface DemoAuthRepository extends MongoRepository<RequestRegistration, String> {

}
