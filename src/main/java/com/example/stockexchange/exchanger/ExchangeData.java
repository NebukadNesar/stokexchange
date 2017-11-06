package com.example.stockexchange.exchanger;

import java.util.List;

/**
 * ExhangeData class contains the requested paramters, while making a request to
 * this appliation you will use this class as the paramters are going to be used
 * in Exchange Stock operations.
 * 
 * @author BURHAN
 *
 */
public class ExchangeData {

	private List<String> countryCode;
	private String category;
	private int baseBid;

	public ExchangeData() {
	}

	public ExchangeData(List<String> countryCode, String category, int baseBid) {
		this.countryCode = countryCode;
		this.category = category;
		this.baseBid = baseBid;
	}

	public List<String> getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(List<String> countryCode) {
		this.countryCode = countryCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getBaseBid() {
		return baseBid;
	}

	public void setBaseBid(int baseBid) {
		this.baseBid = baseBid;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("ExchangeData details: ");
		str.append("\n\tcountries: ");
		str.append(this.getCountryCode().toString());
		str.append("\n\tcategory: ");
		str.append(this.getCategory());
		str.append("\n\tbasebid: ");
		str.append(this.getBaseBid());
		return str.toString();
	}

}
