package com.wallet.dto.request;

import com.wallet.model.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {
	
	private String firstName;
	
	private String lastName;
	
	private String emailId;
	
	private String contactNo;
	
	private Gender gender;
	
	private String password;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String city;
	
	private String state;
	
	private String pincode;

	@Override
	public String toString() {
		return "CustomerRequestDto [firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", contactNo=" + contactNo + ", gender=" + gender + ", password=" + password + ", addressLine1="
				+ addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city + ", state=" + state + ", pincode="
				+ pincode + "]";
	}
	
	
	
}
