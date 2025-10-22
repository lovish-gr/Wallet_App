package com.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.common.ApiResponse;
import com.wallet.dto.request.TransactionDto;
import com.wallet.repo.TransactionRepo;
import com.wallet.service.TransactionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	TransactionService ts;
	
	@PostMapping("/transfer")
	public ResponseEntity<ApiResponse> transfer(@RequestBody TransactionDto data){
		int transactionId = ts.transfer(data);
		ApiResponse apiRes = new ApiResponse(HttpStatus.OK.value(),"transfer successful",transactionId);
		return new ResponseEntity<ApiResponse>(apiRes,HttpStatus.OK);
	}
	
}
