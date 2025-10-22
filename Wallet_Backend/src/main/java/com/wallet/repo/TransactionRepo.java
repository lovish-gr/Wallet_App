package com.wallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer>{

}
