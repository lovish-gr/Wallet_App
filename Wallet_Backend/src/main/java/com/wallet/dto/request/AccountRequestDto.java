package com.wallet.dto.request;

import com.wallet.model.AccountType;
import com.wallet.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto {
	
	
	private int customerId;
	
	private AccountType accType;
	
	private double openingBalance;
	
	private String description;
	
}
