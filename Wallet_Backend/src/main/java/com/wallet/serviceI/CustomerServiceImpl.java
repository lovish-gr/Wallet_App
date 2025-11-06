package com.wallet.serviceI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.controller.CustomerCotroller;
import com.wallet.dto.request.CustomerListReqDto;
import com.wallet.dto.request.CustomerLoginDto;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.dto.response.CustomerResponseDTO;
import com.wallet.model.Address;
import com.wallet.model.Customer;
import com.wallet.model.Email;
import com.wallet.repo.AddressRepo;
import com.wallet.repo.CustomerRepo;
import com.wallet.service.AddressService;
import com.wallet.service.CustomerService;
import com.wallet.service.EmailScheduler;
import com.wallet.service.EmailService;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepo cr;

	@Autowired
	AddressRepo ar;

	@Autowired
	EmailScheduler es;

	private final AddressService as;

	@Value("${app.settings.trailDays}")
	private long trailDays;

	@Override
	public Integer createCustomer(@Valid CustomerRequestDto customerRequst) {
		// TODO Auto-generated method stub

		LocalDate date = null;

		Customer newCust = Customer.builder().firstName(customerRequst.getFirstName())
				.lastName(customerRequst.getLastName()).emailId(customerRequst.getEmailId())
				.contactNo(customerRequst.getContactNo()).password(customerRequst.getPassword())
				.registrationDate(date.now()).lastTrailDate(date.now().plusDays(trailDays))
				.gender(customerRequst.getGender()).address(as.newAdd(customerRequst)).build();
		if (cr.existsByEmailId(customerRequst.getEmailId())) {
			throw new IllegalArgumentException("Email already exists: " + customerRequst.getEmailId());
		}
		Customer res = cr.save(newCust);
		Email email = new Email(customerRequst.getEmailId(), "You have succcessfully created account on our wallet-app",
				"Welcome mail");
		es.scheduleWelcomeEmail(email);
		return res.getCustomerId();
	}

	@Override
	public CustomerResponseDTO customerAuth(CustomerLoginDto custAuth) {
		// TODO Auto-generated method stub
		Customer cust = cr.findByEmailIdAndPassword(custAuth.getEmailId(), custAuth.getPassword());
		CustomerResponseDTO res = new CustomerResponseDTO(cust.getCustomerId(), cust.getFirstName(), cust.getLastName(),
				cust.getEmailId(), cust.getContactNo());
		return res;
	}

	@Override
	public Page<Customer> getPaginated(Pageable pageable) {
		return cr.findAll(pageable);
	}

	public Page<CustomerResponseDTO> getCustomers(CustomerListReqDto request) {

		String[] sortParts = request.getSort().split(",");
		String sortField = sortParts[0];
		Sort.Direction sortDirection = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc")
				? Sort.Direction.DESC
				: Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(sortDirection, sortField));

		Page<Customer> customerPage;
		String search = request.getSearch() != null ? request.getSearch().trim() : "";
		if (search.isEmpty()) {
			customerPage = cr.findAll(pageable);
			return convertToCustomerResponseDTOPage(customerPage);
		} else {
			customerPage = cr.searchCustomers(search, pageable);
			return convertToCustomerResponseDTOPage(customerPage);
		}
	}

	private Page<CustomerResponseDTO> convertToCustomerResponseDTOPage(Page<Customer> customerPage) {
		return new PageImpl<>(
				customerPage.getContent().stream().map(this::convertToCustomerResponseDTO).collect(Collectors.toList()),
				customerPage.getPageable(), customerPage.getTotalElements());
	}

	private CustomerResponseDTO convertToCustomerResponseDTO(Customer customer) {
		CustomerResponseDTO dto = new CustomerResponseDTO();
		dto.customerId = customer.getCustomerId();
		dto.firstname = customer.getFirstName();
		dto.lastName = customer.getLastName();
		dto.emailid = customer.getEmailId();
		dto.contactNo = customer.getContactNo();
		return dto;
	}
}
