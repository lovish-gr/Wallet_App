package com.wallet.serviceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.model.Address;
import com.wallet.repo.AddressRepo;
import com.wallet.service.AddressService;

import jakarta.validation.Valid;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	AddressRepo ar;
	
	@Override
	public Address newAdd(@Valid CustomerRequestDto custReq) {
		// TODO Auto-generated method stub
		Address newAdd = Address.builder().addressLine1(custReq.getAddressLine1())
				.addressLine2(custReq.getAddressLine2()).city(custReq.getCity()).state(custReq.getState()).pincode(custReq.getPincode()).build();
		
		return ar.save(newAdd);
	}

}
