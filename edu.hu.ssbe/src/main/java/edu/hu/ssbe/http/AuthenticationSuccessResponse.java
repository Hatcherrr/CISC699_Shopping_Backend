package edu.hu.ssbe.http;

import edu.hu.ssbe.bean.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticationSuccessResponse extends Response {

	private static final Logger LOGGER = LogManager.getLogger(AuthenticationSuccessResponse.class);
	private Account account;
//	private int id;

	public AuthenticationSuccessResponse(boolean success, int code, String message, Account account) {
		super(success, code, message);
		this.account = account;
		LOGGER.info(account);
//		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

}
