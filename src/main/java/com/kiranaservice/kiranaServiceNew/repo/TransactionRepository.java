package com.kiranaservice.kiranaServiceNew.repo;

import com.kiranaservice.kiranaServiceNew.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
