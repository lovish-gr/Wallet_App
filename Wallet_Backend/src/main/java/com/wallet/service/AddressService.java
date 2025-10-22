package com.wallet.service;

import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.model.Address;

import jakarta.validation.Valid;

public interface AddressService {

	public Address newAdd(@Valid CustomerRequestDto custReq);
}
