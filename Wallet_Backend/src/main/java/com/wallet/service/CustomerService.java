package com.wallet.service;

//import java.awt.print.Pageable;
import java.io.ByteArrayInputStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.request.CustomerListReqDto;
import com.wallet.dto.request.CustomerLoginDto;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.dto.response.CustomerResponseDTO;
import com.wallet.model.Customer;

import jakarta.validation.Valid;

public interface CustomerService {
	public Integer createCustomer(@Valid CustomerRequestDto customerRequst);
	public CustomerResponseDTO customerAuth(CustomerLoginDto custAuth);
	public Page<Customer> getPaginated(Pageable pageable);
	public Page<CustomerResponseDTO> getCustomers(CustomerListReqDto request);
}