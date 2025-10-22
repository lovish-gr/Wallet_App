package com.wallet.serviceI;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wallet.controller.CustomerCotroller;
import com.wallet.dto.request.CustomerLoginDto;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.model.Address;
import com.wallet.model.Customer;
import com.wallet.model.Email;
import com.wallet.repo.AddressRepo;
import com.wallet.repo.CustomerRepo;
import com.wallet.service.AddressService;
import com.wallet.service.CustomerService;
import com.wallet.service.EmailService;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    
	
	@Autowired
	CustomerRepo cr;
	
	@Autowired
	EmailService es;
	
	private final AddressService as;



	@Override
	public Integer createCustomer(@Valid CustomerRequestDto customerRequst) {
		// TODO Auto-generated method stub
		
		LocalDate date = null ;
	
		Customer newCust = Customer.builder().firstName(customerRequst.getFirstName())
				.lastName(customerRequst.getLastName()).emailId(customerRequst.getEmailId())
				.contactNo(customerRequst.getContactNo()).password(customerRequst.getPassword()).registrationDate(date.now())
				.gender(customerRequst.getGender()).address(as.newAdd(customerRequst)).build();
		Customer res = cr.save(newCust);
		Email email = new Email(customerRequst.getEmailId(),"You have succcessfully created account on our wallet-app","Welcome mail");
		String mres = es.sendSimpleMail(email);
		System.out.println(mres);
		return res.getCustomerId();
	}

	@Override
	public Customer customerAuth(CustomerLoginDto custAuth) {
		// TODO Auto-generated method stub
		Customer cust = cr.findByEmailIdAndPassword(custAuth.getEmailId(), custAuth.getPassword());
		System.out.println(cust);
		return cust;
	}
	
	
	
}
