package com.wallet.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.model.Customer;


public interface CustomerRepo extends JpaRepository<Customer, Integer>{
//	public Optional<Customer> findByEmailIdAndPassword(String emailId, String password);
	Customer findByEmailIdAndPassword(String emailId, String password);
	Customer  findByCustomerId(int customerId);
}
