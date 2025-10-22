package com.wallet.service;

import com.wallet.dto.request.AccountRequestDto;

public interface AccountService {
	public int saveAccount(AccountRequestDto accReq);
}
