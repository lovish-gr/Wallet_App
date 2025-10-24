package com.wallet.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wallet.model.Customer;


public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	Customer findByEmailIdAndPassword(String emailId, String password);
	Customer  findByCustomerId(int customerId);
	@Query("SELECT c FROM Customer c WHERE c.lastTrailDate = :today OR c.lastTrailDate = :tomorrow")
	List<Customer> findCustomersExpiringTodayOrTomorrow(LocalDate today, LocalDate tomorrow);
	
}
