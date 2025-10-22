package com.wallet.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDocDto {

	public int customerId;
	
	public MultipartFile aadhaarFile;
	public MultipartFile panFile;
	public MultipartFile passportFile;
	
}
