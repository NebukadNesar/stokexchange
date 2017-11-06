package com.example.stockexchange.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MongoDB mapped object for transactions
 * 
 * @author BURHAN
 *
 */
public class Transaction {

	private String id;
	private String companyName;
	private String transactionCategory;
	private double transactionAmount;
	private String transactionDate;
	private boolean transactionType;

	public Transaction() {
	}

	public Transaction(String companyName, String transactionCategory, double transactionAmount,
			boolean transactionType) {
		this.companyName = companyName;
		this.transactionCategory = transactionCategory;
		this.transactionAmount = transactionAmount;
		this.transactionType = transactionType;
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm aaa");
		this.transactionDate = sdf.format(new Date());
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTransactionCategory() {
		return transactionCategory;
	}

	public void setTransactionCategory(String transactionCategory) {
		this.transactionCategory = transactionCategory;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public boolean isTransactionType() {
		return transactionType;
	}

	public void setTransactionType(boolean transactionType) {
		this.transactionType = transactionType;
	}

}
