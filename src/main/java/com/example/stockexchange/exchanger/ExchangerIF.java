package com.example.stockexchange.exchanger;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.stockexchange.domain.Company;

/**
 * This interface provides the basic middle layer operations to PUBLIC api, such
 * as StockExchangeController
 * 
 * @author BURHAN
 *
 */
public interface ExchangerIF {

	public List<Company> getAll();

	public ResponseEntity<?> buyStock(ExchangeData exchangeData);

	public ResponseEntity<?> getCompanyStatus(String companyId);

	public ResponseEntity<?> getSuccesfullTransanctions();

	public ResponseEntity<?> getAllTransanctions();

}
