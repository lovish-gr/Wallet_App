package com.wallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.model.Account;

public interface AccountRepo extends JpaRepository<Account, Integer> {
	public Account findByAccountNumber(int accNum);
}
