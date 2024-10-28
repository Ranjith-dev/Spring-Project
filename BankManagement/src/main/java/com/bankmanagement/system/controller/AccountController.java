package com.bankmanagement.system.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankmanagement.system.entity.Account;
import com.bankmanagement.system.service.BankService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private BankService bankService;
	
	@PostMapping("/create")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		Account newAccount = bankService.createAccount(account);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<?> getAccountByNumber(@PathVariable("accountNumber") Long accountNumber){
		Optional<Account> optionalAccount = bankService.getAccountDetailsByAccountNumber(accountNumber);
		if(optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			return ResponseEntity.status(HttpStatus.OK).body(account);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!");
		}
	}
	
	@GetMapping()
	public ResponseEntity<List<Account>> getAllAccountsFromDB(){
		List<Account> accounts = bankService.getAllAccountDetails();
		
		return ResponseEntity.status(HttpStatus.OK).body(accounts);
	}
	
	@PutMapping("/deposit/{accountNumber}/{amount}")
	public ResponseEntity<?> depositAmountInAccount(@PathVariable("accountNumber") Long accountNumber, @PathVariable("amount") Double amount){
		Optional<Account> optionalDepositAmount = bankService.depositAmount(accountNumber, amount);
		
		if (optionalDepositAmount.isPresent()) {
			Account depositAmount = optionalDepositAmount.get();
			
			return ResponseEntity.status(HttpStatus.OK).body(depositAmount);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!");

	}
	
	@PutMapping("/withdraw/{accountNumber}/{amount}")
	public ResponseEntity<?> withdrawAmountFromAccount(@PathVariable("accountNumber") Long accountNumber, @PathVariable("amount") Double amount){
		Optional<Account> optionalAccount = bankService.getAccountDetailsByAccountNumber(accountNumber);
		
		if(optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			if(amount <= account.getAccountBalance()) {
				Optional<Account> withdrawAmountFromAccount = bankService.withdrawAmount(accountNumber, amount);
				return ResponseEntity.status(HttpStatus.OK).body(withdrawAmountFromAccount.get());
			}else {
				return ResponseEntity.status(HttpStatus.OK).body("Insufficient Balance!");
			}
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Not found!");
	}
	
	@DeleteMapping("/delete/{accountNumber}")
	public ResponseEntity<String> deleteAccount(@PathVariable("accountNumber") Long accountNumber){
		Optional<Account> closeAccount = bankService.closeAccount(accountNumber);

		if (closeAccount.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body("Account closed successfully!");
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!");
	}
	
}
