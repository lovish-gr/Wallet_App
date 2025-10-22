package com.wallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.model.Address;

public interface AddressRepo extends JpaRepository<Address, Integer> {

}
