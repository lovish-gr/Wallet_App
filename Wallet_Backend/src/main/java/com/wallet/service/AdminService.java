package com.wallet.service;


import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.request.AdminReqDto;
import com.wallet.dto.response.BulkUploadResponse;
import com.wallet.model.Admins;

public interface AdminService {
	
	public BulkUploadResponse uploadCustomers(MultipartFile file);
	public Admins adminAuth(AdminReqDto data);
}
