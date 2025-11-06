package com.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;import com.google.protobuf.Api;
import com.wallet.dto.common.ApiResponse;
import com.wallet.dto.request.TransactionDto;
import com.wallet.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/transaction")
@Slf4j
public class TransactionController {
	
	@Autowired
	TransactionService ts;
	
	@PostMapping("/transfer")
	public ResponseEntity<ApiResponse> transfer(@RequestBody TransactionDto data){
		log.info("request rec");
		int transactionId = ts.transfer(data);
		ApiResponse apiRes = new ApiResponse(HttpStatus.OK.value(),"transfer successful",transactionId);
		return new ResponseEntity<ApiResponse>(apiRes,HttpStatus.OK);
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<ApiResponse> withDraw(@RequestBody TransactionDto data){
		int transactionId = ts.withdraw(data);
		ApiResponse apiRes = new ApiResponse(HttpStatus.OK.value(),"transfer successful",transactionId);
		return new ResponseEntity<ApiResponse>(apiRes,HttpStatus.OK);
	}
	
}
