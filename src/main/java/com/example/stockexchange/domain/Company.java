package com.example.stockexchange.domain;

import java.util.Comparator;
import java.util.List;

/**
 * MongoDB mapped object for companies
 * 
 * @author BURHAN
 *
 */
public class Company implements Comparable<Company> {

	private String id;
	private String companyId;
	private String companyName;
	private List<String> country;
	private double budget;
	private int bid;
	private List<String> category;

	public Company() {
	}

	public Company(String companyId, String companyName, int budget, int bid, List<String> country,
			List<String> category) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.budget = budget;
		this.bid = bid;
		this.country = country;
		this.category = category;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public int getBid() {
		return bid;
	}

	public List<String> getCountry() {
		return country;
	}

	public void setCountry(List<String> country) {
		this.country = country;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("{ Company Details: ");
		str.append("Company: ");
		str.append(this.companyName);
		str.append(", Budget: ");
		str.append(this.budget);
		str.append(", Bid: ");
		str.append(this.bid);
		str.append(", Country: ");
		str.append(this.country.toString() + "}");
		return str.toString();
	}

	public static Comparator<Company> companyBidComparator = new Comparator<Company>() {
		public int compare(Company company1, Company company2) {
			return company1.compareTo(company2);
		}
	};

	public int compareTo(Company company) {
		return Integer.compare(company.getBid(), this.bid);
	}

}
