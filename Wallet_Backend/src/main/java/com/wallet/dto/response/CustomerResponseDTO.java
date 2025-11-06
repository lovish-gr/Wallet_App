package com.wallet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {
	
	public int customerId;
	
	public String firstname;
	
	public String lastName;
	
	public String emailid;
	
	public String contactNo;
	
	
}
