package com.wallet.dto.request;

import lombok.Data;

@Data
public class TransactionDto {

	public int customerId;
	
	public int accountNumber;
	
	public int amount;
	
	public String description;
	
	public int fromAccountNumber;
}
