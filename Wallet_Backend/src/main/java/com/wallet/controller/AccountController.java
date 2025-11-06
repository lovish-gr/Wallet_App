package com.wallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.common.ApiResponse;
import com.wallet.dto.request.AccountRequestDto;
import com.wallet.dto.response.AllAccountResponse;
import com.wallet.service.AccountService;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

	@Autowired
	AccountService accountService;

	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createAccount(@RequestBody AccountRequestDto data) {
		int accId = accountService.saveAccount(data);
		ApiResponse res = new ApiResponse(HttpStatus.OK.value(), "account created", accId);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);

	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<AllAccountResponse>> getAccountsByCustomerId(@PathVariable Integer customerId) {
		List<AllAccountResponse> response = accountService.getAccountsByCustomerId(customerId);
		return ResponseEntity.ok(response);
	}
}
