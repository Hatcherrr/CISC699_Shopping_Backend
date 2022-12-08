package edu.hu.ssbe.handlers;

import edu.hu.ssbe.bean.Account;
import edu.hu.ssbe.service.AccountService;
import edu.hu.ssbe.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private AccountService accountService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String username = authentication.getName();
		Account account = accountService.getAccountByUsername(username);
//		es.sendSimpleMessage(u.getEmail(), "sucessfully login", "You have been login");
		Account acnt = Account.builder()
				.id(account.getId())
				.email(account.getEmail())
				.phone(account.getPhone())
				.password(account.getPassword())
				.firstname(account.getFirstname())
				.lastname(account.getLastname())
				.kind(account.getKind())
				.build();
		SecurityUtils.sendAuthenticationSuccessResponse(response, HttpServletResponse.SC_OK, "Login successfully.", null, acnt);
	}
	
}
