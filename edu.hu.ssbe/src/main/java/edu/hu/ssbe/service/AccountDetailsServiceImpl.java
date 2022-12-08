package edu.hu.ssbe.service;

import edu.hu.ssbe.bean.Account;
import edu.hu.ssbe.dao.AccountDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// This class will only be used by Spring Security to verify the login information
@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOGGER = LogManager.getLogger(AccountDetailsServiceImpl.class);

	@Autowired
	private AccountDao accountDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("-----------username: {}", username);
		Account account = accountDao.findOneByUsername(username);
		
		if(account == null) {
			throw new UsernameNotFoundException("Account: " + username + " doesn't exist!");
		}
		
		return account;
	}

}
