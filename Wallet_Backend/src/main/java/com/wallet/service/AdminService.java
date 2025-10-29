package com.wallet.service;

import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.response.BulkUploadResponse;

public interface AdminService {
	
	public BulkUploadResponse uploadCustomers(MultipartFile file);
}
