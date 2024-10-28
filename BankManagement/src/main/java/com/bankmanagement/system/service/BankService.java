package com.bankmanagement.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bankmanagement.system.entity.Account;

@Service
public interface BankService {

	public Account createAccount(Account account);
	
	public Optional<Account> getAccountDetailsByAccountNumber(Long accountNumber);
	
	public List<Account> getAllAccountDetails();
	
	public Optional<Account> depositAmount(Long accountNumber, Double amount);
	
	public Optional<Account> withdrawAmount(Long accountNumber, Double amount);
	
	public Optional<Account> closeAccount(Long accountNumber);
}
