package com.wallet.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AdminReqDto {
	
	public String email;
	
	public String password;
}
