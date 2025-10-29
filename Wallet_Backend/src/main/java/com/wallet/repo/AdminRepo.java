package com.wallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.model.Admins;

public interface AdminRepo extends JpaRepository<Admins, Integer> {
	
	boolean existsByEmail(String email);
}
