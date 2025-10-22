package com.wallet.service;

import com.wallet.dto.request.TransactionDto;

public interface TransactionService {
	
	public int transfer(TransactionDto tranReq);
	public int withdraw(TransactionDto tranReq);
	public int deposite(TransactionDto tranReq);
}
