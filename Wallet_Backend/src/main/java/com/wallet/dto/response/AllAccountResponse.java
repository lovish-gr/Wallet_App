package com.wallet.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.wallet.model.Account;
import com.wallet.model.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllAccountResponse {

	private int accountNumber;
    private String customerName;
    private AccountType accType;
    private double openingBalance;
    private LocalDate openingDate;
    private String description;

}
