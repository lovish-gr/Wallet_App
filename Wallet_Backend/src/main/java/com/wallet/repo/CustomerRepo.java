package com.wallet.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wallet.dto.response.CustomerResponseDTO;
import com.wallet.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	Customer findByEmailIdAndPassword(String emailId, String password);

	Customer findByCustomerId(int customerId);

	@Query("SELECT c FROM Customer c WHERE c.lastTrailDate = :today OR c.lastTrailDate = :tomorrow")
	List<Customer> findCustomersExpiringTodayOrTomorrow(LocalDate today, LocalDate tomorrow);

	Boolean existsByEmailId(String emailId);

	Page<Customer> findAll(Pageable pageable);

	@Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(c.emailId) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<Customer> searchCustomers(@Param("search") String search, Pageable pageable);
}
