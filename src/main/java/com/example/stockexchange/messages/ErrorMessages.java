package com.example.stockexchange.messages;

import java.util.HashMap;
import java.util.Map;

/**
 * This class does one thing, and it is meant to create response messages
 * according to response codes which we have already defined and send them to
 * the API
 * 
 * @author BURHAN
 *
 */
public abstract class ErrorMessages {

	private static Map<String, String> errorMessages;

	public static String errorMessage(int errorCode) {
		if (errorMessages == null)
			populateErrorMessages();

		return errorMessages.get("" + errorCode);
	}

	/**
	 * We could have extends messages but, that looks enough for the current
	 * being
	 */
	private static void populateErrorMessages() {
		errorMessages = new HashMap<String, String>() {
			{
				put("100", "Winner = ");
				put("101", "");
				put("102", "OK");
				put("103", "");
				put("104", "");
				put("104", "");
				put("400", "No company matched");
				put("401", "No company found");
				put("402", "No transaction found");
				put("403", "No successfull transactions found");
				put("404", "Not company found with that id");
				put("405", "");
				put("406", "");
				put("407", "No Companies Passed from BaseBid");
				put("408", "No Companies Passed from Budget");
				put("409", "No Companies Passed from Targeting");
				put("410", "");
				put("411", "");
			}
		};

	}

}
