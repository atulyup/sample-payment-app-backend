package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.TransactionModel;

public interface DemoTransactionRepository extends MongoRepository<TransactionModel, Integer> {

}
