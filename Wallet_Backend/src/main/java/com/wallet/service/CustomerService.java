package com.wallet.service;

import java.io.ByteArrayInputStream;

import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.request.CustomerLoginDto;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.model.Customer;

import jakarta.validation.Valid;

public interface CustomerService {
	public Integer createCustomer(@Valid CustomerRequestDto customerRequst);
	public Customer customerAuth(CustomerLoginDto custAuth);
	public String saveCustomerFromExcel(MultipartFile file);
	public ByteArrayInputStream downloadSampleExcel();
}
