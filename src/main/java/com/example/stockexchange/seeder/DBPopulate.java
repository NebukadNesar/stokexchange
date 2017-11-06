package com.example.stockexchange.seeder;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.stockexchange.domain.Company;
import com.example.stockexchange.repository.CompanyRepository;



/**
 * This class helps us populate some bulk data into MongoDb before even we start
 * using the application.
 * 
 * Here, in every run of the application we drop all the information in database
 * and rebuild it except the Transaction informations
 * 
 * We may drop everything at wish with just using the related repository
 * interface , For instance, in below example we have used companyRepository, So
 * we can also use transactionRepository to drop all the information about
 * transaction from the previous session created, or maybe even add some bulk
 * fake data.
 * 
 * @author BURHAN
 */

@Component
public class DBPopulate implements CommandLineRunner {

	@Autowired
	private CompanyRepository companyRepository;
	
	private static final Logger LOG = Logger.getLogger(DBPopulate.class);
	
	public DBPopulate(CompanyRepository companyRepository) {
		this.companyRepository= companyRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		LOG.info("...Populating some company data into database...");
		Company companyOne = new  Company(
				"C1",
				"Company1",
				100,
				9,
				Arrays.asList(new String("IN"), new String("FR")),
				Arrays.asList(new String("IT"),new String("Finance"))
		);
		Company companyTwo = new  Company(
				"C2",
				"Company2",
				400,
				18,
				Arrays.asList(new String("US"), new String("FR")),
				Arrays.asList(new String("Automobile"),new String("IT"))
		);
		Company companyThree = new  Company(
				"C3",
				"Company3",
				100,
				13,
				Arrays.asList(new String("US"), new String("IN")),
				Arrays.asList(new String("Automobile"),new String("Finance"))
		);
		Company companyFour = new  Company(
				"C4",
				"Company4",
				1110,
				13,
				Arrays.asList(new String("IN")),
				Arrays.asList(new String("Automobile"),new String("Finance"))
		);
		
		companyRepository.deleteAll();
		List<Company> list =  Arrays.asList(companyOne, companyTwo, companyThree, companyFour);
		companyRepository.save(list);
	}

}
