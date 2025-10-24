package com.wallet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

	public int customerId;
	
	public int accountNumber;
	
	public int amount;
	
	public String description;
	
	public int fromAccountNumber;
}
