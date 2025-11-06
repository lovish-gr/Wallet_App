package com.wallet.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wallet.model.Account;
import com.wallet.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer>{
	
	List<Transaction> findByFrmaccfk(Account account);
}
