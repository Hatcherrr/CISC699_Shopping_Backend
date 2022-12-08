package edu.hu.ssbe.controller;

import edu.hu.ssbe.bean.Account;
import edu.hu.ssbe.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ds/auth")
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(
            path = "/login",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity login(@RequestBody Account account) {
        LOGGER.info("---------account: {}", account);
        Account acnt = accountService.getAccountByUsername(account.getUsername());
        if (acnt != null) {
            return ResponseEntity.ok(acnt);
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(
            path = "/logout",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity logout(@RequestBody Account account) {
        Account createdAccount = accountService.addAccount(account);
        if (createdAccount != null) {
            return ResponseEntity.ok(createdAccount);
        }
        return ResponseEntity.badRequest().build();
    }
}
