package com.wallet.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.dto.response.AllAccountResponse;
import com.wallet.model.Account;

public interface AccountRepo extends JpaRepository<Account, Integer> {
	public Account findByAccountNumber(int accNum);
	List<Account> findByCustomerCustomerId(int custId);
}
