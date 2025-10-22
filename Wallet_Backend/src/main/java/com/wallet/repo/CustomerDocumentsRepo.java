package com.wallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.model.CustomerDocuments;

public interface CustomerDocumentsRepo extends JpaRepository<CustomerDocuments, Integer>{
	public CustomerDocuments findByCustomerCustomerId(int custId);
}
