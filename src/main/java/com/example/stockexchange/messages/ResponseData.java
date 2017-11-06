package com.example.stockexchange.messages;

/**
 * This class is used to send response messages to user in entity response
 * object
 * 
 * Messages and reponse code are retreived at the time of action processes.
 *  
 * So we have 100 -> 400 messages, these are all made up response codes and messages.
 *
 */
public class ResponseData {

	private String responseMessage;
	private int reponseCode;

	public ResponseData() {
	}

	public ResponseData(int responseCode) {
		this.reponseCode = responseCode;
		this.responseMessage = ErrorMessages.errorMessage(responseCode);
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getReponseCode() {
		return reponseCode;
	}

	public void setReponseCode(int reponseCode) {
		this.reponseCode = reponseCode;
	}

	public void appendMessage(String winnerCompanyID) {
		this.responseMessage += winnerCompanyID;
	}

}
