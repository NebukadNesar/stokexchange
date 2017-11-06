package com.example.stockexchange.service;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.stockexchange.exchanger.ExchangeData;
import com.example.stockexchange.exchanger.Exchanger;
import com.example.stockexchange.repository.CompanyRepository;
import com.example.stockexchange.repository.TransactionRepository;

/**
 * StockExchangeController provides the publicly accessible methods to outside
 * users. Users can access and use these methods as a "request url" For example
 * they can use "curl" from command line, they can use Postman, or browsers to
 * make calls to this service
 * 
 * Generally spring applications start to serve from http://localhost:8080, in
 * this case we can derivate the api visibility and naming by playing with the
 * "RequestMapping" annotation
 * 
 * In this class we have "/stock" as the starting point after localhost, so the request will be like http://localhost:8080/stock
 * 
 * @author BURHAN
 *
 */
@RestController
@RequestMapping("/stock")
public class StockExchangeController {

	private static final Logger LOG = Logger.getLogger(StockExchangeController.class);
	private Exchanger stockExchanger;

	public StockExchangeController(CompanyRepository companyRepository, TransactionRepository transactionRepository) {
		stockExchanger = new Exchanger(companyRepository, transactionRepository);
	}

	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCompanies() {
		LOG.info("/stock/companies request received..");
		return new ResponseEntity<>(stockExchanger.getAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/company/companyId={companyId}", method = RequestMethod.GET)
	public ResponseEntity<?> getCompany(@PathVariable String companyId) {
		LOG.info("/stock/companies request received..");
		ResponseEntity<?> result = stockExchanger.getCompanyStatus(companyId);
		return result;
	}

	@RequestMapping(value = "/exchange", method = RequestMethod.POST)
	public ResponseEntity<?> stockExchange(@RequestBody ExchangeData exchangeData) {
		LOG.info("/stock/company/companyId={companyId} request received..");
		ResponseEntity<?> responseEntity = stockExchanger.buyStock(exchangeData);
		return responseEntity;
	}

	@RequestMapping("/successfultransactions")
	public ResponseEntity<?> getAllSuccesfulTransactions() {
		LOG.info("/stock/successfultransactions request received..");
		return new ResponseEntity<>(stockExchanger.getSuccesfullTransanctions(), HttpStatus.OK);
	}

	@RequestMapping(value = "/alltransactions", method = RequestMethod.GET)
	public ResponseEntity<?> getAllTransactions() {
		LOG.info("/stock/alltransactions request received..");
		return new ResponseEntity<>(stockExchanger.getAllTransanctions(), HttpStatus.OK);
	}
}
