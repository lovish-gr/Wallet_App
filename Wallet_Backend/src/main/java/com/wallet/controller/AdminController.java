package com.wallet.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.common.ApiResponse;
import com.wallet.dto.request.AdminReqDto;
import com.wallet.dto.response.BulkUploadResponse;
import com.wallet.repo.AdminRepo;
import com.wallet.service.AdminService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private AdminService as;
    
    private final AdminRepo ar;

    
    
    
    @PostMapping("/auth")
    public ResponseEntity<ApiResponse> auth(@RequestBody AdminReqDto data){
    	
    	if(ar.existsByEmail(data.getEmail())) {
    		ApiResponse res = new ApiResponse(HttpStatus.OK.value(),"admin verified",true);
    		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
    	}else {
    		ApiResponse res = new ApiResponse(HttpStatus.UNAUTHORIZED.value(),"admin not verified", false);
    		return new ResponseEntity<ApiResponse>(res,HttpStatus.UNAUTHORIZED);
    	}
    	
    }

    @PostMapping("/upload")
    public ResponseEntity<BulkUploadResponse> uploadCustomers(@RequestParam("file") MultipartFile file) {
        BulkUploadResponse response = as.uploadCustomers(file);
        return ResponseEntity.ok(response);
    }
    
    

}
