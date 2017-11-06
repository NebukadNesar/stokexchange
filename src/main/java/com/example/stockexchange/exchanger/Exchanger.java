package com.example.stockexchange.exchanger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.stockexchange.domain.Company;
import com.example.stockexchange.domain.Transaction;
import com.example.stockexchange.messages.ResponseData;
import com.example.stockexchange.repository.CompanyRepository;
import com.example.stockexchange.repository.TransactionRepository;
import com.mongodb.Mongo;

/**
 * This class is the middle layer service definition that all the ugly works are
 * processed, for example calculations, insertions, queries etc.. Implements the
 * echanger interface. Almost all the methods will return results as in
 * ResponseEntity object so that the TYPE won't matter for the end points.
 * 
 * @author BURHAN
 *
 */
@Service
public class Exchanger implements ExchangerIF {

	/**
	 * Logger for general reporting
	 */
	private static final Logger Log = Logger.getLogger(Exchanger.class);

	/**
	 * Autowired means that we have a Db related object created for the mapping
	 * in Mongo database, This object (Company, Transaction .. etc..) can be
	 * access via these repositoriess Spring wil automatically recognize them
	 * and give us the available methods to query database.
	 */
	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	/**
	 * Here we have a mongodb tempate to access our database directly, we can do
	 * CRUD operations with this template.
	 */
	private MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(), "stockexchange"));

	public Exchanger(CompanyRepository companyRepository, TransactionRepository transactionRepository) {
		this.companyRepository = companyRepository;
		this.transactionRepository = transactionRepository;
	}

	/**
	 * once a request reaches here, we select the companies that matches the
	 * criteria of country codes & category, then we check if the companies pass
	 * our validations, at the end we pick the successful one and return it with
	 * and also update its budget in database
	 */
	@Async
	public ResponseEntity<?> buyStock(ExchangeData exchangeData) {
		Log.info("Request reached to exchangeStock..");
		List<String> countryCodes = exchangeData.getCountryCode();
		String category = exchangeData.getCategory();
		int baseBid = exchangeData.getBaseBid();

		if (countryCodes == null || countryCodes.isEmpty()) {
			updateTransactions(countryCodes.toString(), category, baseBid, false);
			return new ResponseEntity<>(new ResponseData(409), HttpStatus.NOT_FOUND);
			// no country code matched error message
		}

		Query filterQuery = new Query();
		filterQuery.addCriteria(Criteria.where("country").in(countryCodes).and("category").in(category));

		List<Company> filteredCompanies = mongoOps.find(filterQuery, Company.class);
		List<Company> allCompanies = companyRepository.findAll();

		HashMap<String, String> companyMatchResult = new HashMap<>();

		allCompanies.stream().forEach(company -> {
			companyMatchResult.put(company.getCompanyId(), "Failed");
		});

		StringBuilder budgetListLog = new StringBuilder();
		StringBuilder baseBidLog = new StringBuilder();

		budgetListLog.append("BudgetCheck: ");
		baseBidLog.append("BaseBid: ");

		List<Company> budgetCheckList = new ArrayList<>();
		List<Company> baseBidCheckList = new ArrayList<>();

		filteredCompanies.stream().forEach(company -> {
			companyMatchResult.put(company.getCompanyId(), "Passed");
			if (company.getBudget() > 0) {
				budgetCheckList.add(company);
				budgetListLog.append("{" + company.getCompanyId() + ", Passed} ");
				if (company.getBid() >= baseBid) {
					baseBidCheckList.add(company);
					baseBidLog.append("{" + company.getCompanyId() + ", Passed} ");
				} else {
					baseBidLog.append("{" + company.getCompanyId() + ", Failed} ");
				}
			} else {
				budgetListLog.append("{" + company.getCompanyId() + ", Failed} ");
			}
		});

		Log.info("BaseTargeting: " + companyMatchResult.toString());

		if (budgetCheckList.isEmpty()) {
			updateTransactions(countryCodes.toString(), category, baseBid, false);
			return new ResponseEntity<>(new ResponseData(408), HttpStatus.NOT_FOUND);
			// budget failed message
		}

		Log.info(budgetListLog.toString());

		/***********************************************************************
		 * At this point we don't have to check all the companies for 'bid' vs
		 * 'baseBid', either some of the companies are already filtered due to
		 * low budget and/or NO country code provided related with that company
		 ***********************************************************************/

		if (baseBidCheckList.isEmpty()) {
			updateTransactions(countryCodes.toString(), category, baseBid, false);
			return new ResponseEntity<>(new ResponseData(407), HttpStatus.NOT_FOUND);
			// basebid failed message
		}

		if (baseBidCheckList.size() > 1)
			baseBidCheckList.sort(Company.companyBidComparator);

		Log.info(baseBidLog.toString());

		String winnerCompanyID = baseBidCheckList.get(0).getCompanyId();
		ResponseData resultMessage = new ResponseData(100); // winner message
		resultMessage.appendMessage(winnerCompanyID);
		updateTargetCompanyBudget(baseBidCheckList.get(0), baseBid, category);

		return new ResponseEntity<>(resultMessage, HttpStatus.OK);

	}

	private void updateTargetCompanyBudget(Company company, int bid, String category) {
		double companyBudget = (company.getBudget() * 100 - bid) / 100;
		company.setBudget(companyBudget);
		companyRepository.save(company);
		updateTransactions(company.getCompanyName(), category, bid, true);
	}

	/**
	 * update transaction even if its failed or successful
	 */
	private void updateTransactions(String companyName, String category, int bid, boolean typeSuccorFail) {
		Transaction transaction = new Transaction(companyName, category, bid, typeSuccorFail);
		transactionRepository.save(transaction);
	}

	/**
	 * get All companies, along with their details
	 * 
	 * @return
	 */
	public List<Company> getAll() {
		List<Company> list = companyRepository.findAll();
		return list;
	}

	/**
	 * get single company status
	 * 
	 * @param companyId
	 * @return
	 */
	public ResponseEntity<?> getCompanyStatus(String companyId) {
		Company company = companyRepository.findCompanyByCompanyId(companyId);
		if (company == null) {
			return new ResponseEntity<>(new ResponseData(404), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(company, HttpStatus.OK);
		}
	}

	/**
	 * get all transactions successful
	 * 
	 * @return
	 */

	public ResponseEntity<?> getSuccesfullTransanctions() {
		List<Transaction> list = transactionRepository.findTransactionByTransactionType(true);
		if (list == null) {
			return new ResponseEntity<>(new ResponseData(403), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	/**
	 * get all transactions successful & failed
	 * 
	 * @return
	 */
	public ResponseEntity<?> getAllTransanctions() {
		List<Transaction> list = transactionRepository.findAll();
		if (list == null) {
			return new ResponseEntity<>(new ResponseData(402), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}
}
