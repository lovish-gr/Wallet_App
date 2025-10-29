package com.wallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.common.ApiResponse;
import com.wallet.dto.request.AccountRequestDto;
import com.wallet.dto.response.AllAccountResponse;
import com.wallet.model.Account;
import com.wallet.service.AccountService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value ="/account")
public class AccountController {
	
	@Autowired
	AccountService as;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> saveAcc(@RequestBody AccountRequestDto data){
		int accId = as.saveAccount(data);
		
			ApiResponse res = new ApiResponse(HttpStatus.OK.value(),"account created",accId);
			return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
		
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<AllAccountResponse>> getAccountsByCustomerId(@PathVariable Integer customerId) {
	    List<Account> accounts = as.getAccountsByCustomerId(customerId);
	    List<AllAccountResponse> response = accounts.stream()
	            .map(acc -> new AllAccountResponse(
	                    acc.getAccountNumber(),
	                    acc.getCustomer().getFirstName() + " " + acc.getCustomer().getLastName(),
	                    acc.getAccType(),
	                    acc.getOpeningBalance(),
	                    acc.getOpeningDate(),
	                    acc.getDescription()
	            ))
	            .toList();
	    return ResponseEntity.ok(response);
	}
}
