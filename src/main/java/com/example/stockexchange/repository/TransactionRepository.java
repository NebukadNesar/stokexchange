package com.example.stockexchange.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.stockexchange.domain.Transaction;

/**
 * Repositories provies access to the basic CRUD operation in mongo database
 * 
 */
public interface TransactionRepository extends MongoRepository<Transaction, String> {

	public List<Transaction> findAll();

	public List<Transaction> findTransactionByTransactionType(Boolean transactionType);

}
