package com.bankmanagement.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankmanagement.system.entity.Account;
import com.bankmanagement.system.repository.BankRepository;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private BankRepository bankRepository;
	
	@Override
	public Account createAccount(Account account) {
		Account newAccount = bankRepository.save(account);
		return newAccount;
	}

	@Override
	public Optional<Account> getAccountDetailsByAccountNumber(Long accountNumber) {
		Optional<Account> optionalAccount = bankRepository.findById(accountNumber);
		return optionalAccount;
	}

	@Override
	public List<Account> getAllAccountDetails() {
		List<Account> allAccounts = bankRepository.findAll();
		return allAccounts;
	}

	@Override
	public Optional<Account> depositAmount(Long accountNumber, Double amount) {
		Optional<Account> optionalAccount = bankRepository.findById(accountNumber);
		
		if (optionalAccount.isPresent()) {
			Account depositAccount = optionalAccount.get();
			depositAccount.setAccountBalance(depositAccount.getAccountBalance() + amount);
			bankRepository.save(depositAccount);
			
			return Optional.of(depositAccount);
		}
		
		return Optional.empty();
	}

	@Override
	public Optional<Account> withdrawAmount(Long accountNumber, Double amount) {
		Optional<Account> optionalAccount = bankRepository.findById(accountNumber);
		
		if (optionalAccount.isPresent()) {
			Account withdrawAccount = optionalAccount.get();
			withdrawAccount.setAccountBalance(withdrawAccount.getAccountBalance() - amount);
			
			bankRepository.save(withdrawAccount);
			
			return Optional.of(withdrawAccount);
		}
		
		return Optional.empty();
	}

	@Override
	public Optional<Account> closeAccount(Long accountNumber) {
		Optional<Account> optionalAccount = bankRepository.findById(accountNumber);
		
		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			bankRepository.delete(account);
			
			return Optional.of(account);
		}
		
		return Optional.empty();
	}

}
