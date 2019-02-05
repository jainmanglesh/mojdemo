package com.test.mojdemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.mojdemo.domain.Account;
import com.test.mojdemo.exception.MojAccountDoesNotExistException;
import com.test.mojdemo.exception.MojAccountAlreadyExistsException;
import com.test.mojdemo.service.MojService;

/**
 * @author Manglesh Jain
 * Controller to handle the request
 */
@Controller
@RequestMapping("/account")
public class MojController {

	@Autowired
	MojService mojService;
	
    @GetMapping("/getAllAccount")
    public ResponseEntity<List<Account>> getAllAccount() {
        return new ResponseEntity<>(mojService.getAllAccounts(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAccount/{accountId}")
    public ResponseEntity<String> deleteAccountById(@PathVariable("accountId") String accountId) {
    	String message = null;
    	HttpStatus httpStatus = null;

        try {
        	message = mojService.deleteAccountById(accountId);
        	httpStatus = HttpStatus.OK;
        } catch (MojAccountDoesNotExistException e) {
        	message = e.getMessage();
        	httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(message, httpStatus);
    }

    @PostMapping("/saveAccount")
    public ResponseEntity<String> saveAccount(@RequestBody @Valid Account account) {
    	String message = null;
    	HttpStatus httpStatus = null;
        try {
        	message = mojService.saveAccount(account);
        	httpStatus = HttpStatus.CREATED;
        } catch (MojAccountAlreadyExistsException e) {
        	message = e.getMessage();
        	httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(message, httpStatus);
    }

}

