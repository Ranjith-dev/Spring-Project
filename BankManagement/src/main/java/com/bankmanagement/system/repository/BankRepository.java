package com.bankmanagement.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankmanagement.system.entity.Account;

public interface BankRepository extends JpaRepository<Account, Long>{

}
