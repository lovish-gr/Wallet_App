package com.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.common.ApiResponse;
import com.wallet.dto.request.AccountRequestDto;
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
}
