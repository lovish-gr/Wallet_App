package com.wallet.serviceI;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.dto.request.AccountRequestDto;
import com.wallet.model.Account;
import com.wallet.model.Customer;
import com.wallet.repo.AccountRepo;
import com.wallet.repo.CustomerRepo;
import com.wallet.service.AccountService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl  implements AccountService{
	
	private final CustomerRepo cr;
	
	@Autowired
	AccountRepo ar;
	
	
	public int saveAccount(AccountRequestDto accReq) {
		
		Customer cust = cr.findByCustomerId(accReq.getCustomerId());
		Account newAcc = Account.builder().accType(accReq.getAccType()).customer(cust).openingBalance(accReq.getOpeningBalance()).openingDate(LocalDate.now()).description(accReq.getDescription()).build();
		return ar.save(newAcc).getAccountNumber();
	}

}
