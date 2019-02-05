package com.test.mojdemo.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.test.mojdemo.domain.Account;
import com.test.mojdemo.exception.MojAccountDoesNotExistException;
import com.test.mojdemo.exception.MojAccountAlreadyExistsException;

@Service
public class MojService {
	
	//declare a hashmap to hold account details
	private Map<String, Account> mapAccount = new ConcurrentHashMap<>();


	public List<Account> getAllAccounts() {
		return mapAccount.values().stream().collect(Collectors.toList());
	}

	public String deleteAccountById(String accountId) throws MojAccountDoesNotExistException {
		//check if account exists
		if(mapAccount.containsKey(accountId)) {
			mapAccount.remove(accountId);
		} else {
			throw new MojAccountDoesNotExistException("Account doesn't exist");
		}
		return "Account successfully deleted";
	}

	public String saveAccount(Account account) throws MojAccountAlreadyExistsException {
		account.setId(UUID.randomUUID().toString());
		//check if account already exists (account number should be unique)
		if(!(mapAccount.values().stream().anyMatch(value -> account.getAccountNumber().equals(value.getAccountNumber())))) {
			mapAccount.put(account.getId(), account);
		} else {
			throw new MojAccountAlreadyExistsException("Account already exists");
		}
		return "Account successfully saved";
	}

}

