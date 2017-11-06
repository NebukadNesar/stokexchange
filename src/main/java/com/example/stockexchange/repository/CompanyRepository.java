package com.example.stockexchange.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.stockexchange.domain.Company;

/**
 * Repositories provies access to the basic CRUD operation in mongo database
 * 
 */
public interface CompanyRepository extends MongoRepository<Company, String> {

	public List<Company> findAll();

	public Company findCompanyByCompanyId(String companyId);

	public Company findOne(String companyId);

}
