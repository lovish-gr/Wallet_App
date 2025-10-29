package com.wallet.service;

import java.util.List;

import com.wallet.dto.request.AccountRequestDto;
import com.wallet.dto.response.AllAccountResponse;
import com.wallet.model.Account;

public interface AccountService {
	public int saveAccount(AccountRequestDto accReq);
	List<Account> getAccountsByCustomerId(Integer customerId);
}
