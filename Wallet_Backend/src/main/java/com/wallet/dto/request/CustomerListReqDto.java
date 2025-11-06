package com.wallet.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomerListReqDto {
	
	private int page;
	private int size;
	private String sort;
	private String search;
}
