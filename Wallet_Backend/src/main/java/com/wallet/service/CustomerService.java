package com.wallet.service;

import com.wallet.dto.request.CustomerLoginDto;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.model.Customer;

import jakarta.validation.Valid;

public interface CustomerService {
	public Integer createCustomer(@Valid CustomerRequestDto customerRequst);
	public Customer customerAuth(CustomerLoginDto custAuth);
}
