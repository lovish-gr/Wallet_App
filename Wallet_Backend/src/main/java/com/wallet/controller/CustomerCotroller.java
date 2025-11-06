package com.wallet.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;
import com.wallet.dto.common.ApiResponse;
import com.wallet.dto.request.CustomerDocDto;
import com.wallet.dto.request.CustomerLoginDto;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.dto.response.CustomerResponseDTO;
import com.wallet.model.Customer;
import com.wallet.model.CustomerDocuments;
import com.wallet.model.Gender;
import com.wallet.model.Transaction;
import com.wallet.service.CustomerDocumentsService;
import com.wallet.service.CustomerService;
import com.wallet.service.PdfService;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerCotroller {

	@Autowired
	CustomerService cs;

	private final CustomerDocumentsService cds;
	private final PdfService ps;

	@PostMapping(value = "/signup")
	public ResponseEntity<ApiResponse> createCustomer(@RequestBody @Valid CustomerRequestDto custReq) {

		log.info("create api called" + new Date());
		Integer genereatedCustId = cs.createCustomer(custReq);

		ApiResponse apiRes = new ApiResponse(HttpStatus.OK.value(), "customer created", genereatedCustId);
		return new ResponseEntity<ApiResponse>(apiRes, HttpStatus.OK);
	}

	@PostMapping(value = "/custDoc", consumes = "multipart/form-data")
	public ResponseEntity<ApiResponse> custDoc(@ModelAttribute CustomerDocDto data) {
		try {
			String uploadDir = "/Users/lovishgrover/Downloads/doc";
			File dir = new File(uploadDir);
			if (!dir.exists())
				dir.mkdirs();

			File aadhaarDir = new File(uploadDir + "/aadhaar/");
			if (!aadhaarDir.exists())
				aadhaarDir.mkdirs();
			File aadharPath = new File(aadhaarDir,
					data.getCustomerId() + "_AC" + data.getAadhaarFile().getOriginalFilename());
			if (data.getAadhaarFile() != null && !data.getAadhaarFile().isEmpty()) {
				data.getAadhaarFile().transferTo(aadharPath);
			}

			File panDir = new File(uploadDir + "/pan/");
			if (!panDir.exists())
				panDir.mkdirs();
			File panPath = new File(panDir, data.getCustomerId() + "_P" + data.getPanFile().getOriginalFilename());
			if (data.getPanFile() != null && !data.getPanFile().isEmpty()) {
				data.getPanFile().transferTo(panPath);
			}

			File passportDir = new File(uploadDir + "/passport/");
			if (!passportDir.exists())
				passportDir.mkdirs();
			File passportPath = new File(passportDir,
					data.getCustomerId() + "_PP" + data.getPassportFile().getOriginalFilename());

			if (data.getPassportFile() != null && !data.getPassportFile().isEmpty()) {
				data.getPassportFile().transferTo(passportPath);
			}
			cds.newDoc(data.getCustomerId(), aadharPath.getAbsolutePath(), panPath.getAbsolutePath(),
					passportPath.getAbsolutePath());

			ApiResponse apiRes = new ApiResponse(HttpStatus.OK.value(), "Files saved successfully", true);
			return new ResponseEntity<>(apiRes, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiRes = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "File not saved",
					e.getMessage());
			return new ResponseEntity<>(apiRes, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/files")
	public ResponseEntity<UrlResource> getFile(@RequestParam String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				return ResponseEntity.notFound().build();
			}

			Path filePath = file.toPath();
			UrlResource resource = new UrlResource(filePath.toUri());
			String contentType = Files.probeContentType(filePath);

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/custDoc/{customerId}")
	public ResponseEntity<ApiResponse> getCustDoc(@PathVariable int customerId) {
		try {
			CustomerDocuments docData = cds.getDoc(customerId);

			if (docData == null) {
				ApiResponse apiRes = new ApiResponse(HttpStatus.NOT_FOUND.value(), "No documents found", false);
				return new ResponseEntity<>(apiRes, HttpStatus.NOT_FOUND);
			}

			Map<String, String> paths = new HashMap<>();
			paths.put("aadhaarFilePath", docData.getAadhaarFilePath());
			paths.put("panFilePath", docData.getPanFilePath());
			paths.put("passportFilePath", docData.getPassportFilePath());

			ApiResponse apiRes = new ApiResponse(HttpStatus.OK.value(), "Documents fetched successfully", paths);
			return new ResponseEntity<>(apiRes, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiRes = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to fetch documents",
					e.getMessage());
			return new ResponseEntity<>(apiRes, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/custDoc/{customerId}/{type}")
	public ResponseEntity<ApiResponse> deleteDocument(@PathVariable int customerId, @PathVariable String type) {
		try {
			String uploadDir = "/Users/lovishgrover/Downloads/doc";
			String docType = type.toLowerCase();

			// Validate document type
			if (!docType.matches("aadhaar|pan|passport")) {
				ApiResponse apiRes = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Invalid document type", false);
				return new ResponseEntity<>(apiRes, HttpStatus.BAD_REQUEST);
			}

			// Construct directory path based on type
			File docDir = new File(uploadDir + "/" + docType + "/");

			if (!docDir.exists()) {
				ApiResponse apiRes = new ApiResponse(HttpStatus.NOT_FOUND.value(), "Document directory not found",
						false);
				return new ResponseEntity<>(apiRes, HttpStatus.NOT_FOUND);
			}

			// Find and delete the file with matching customerId prefix
			File[] files = docDir.listFiles();
			boolean fileDeleted = false;
			String prefix = "";

			switch (docType) {
			case "aadhaar":
				prefix = customerId + "_AC";
				break;
			case "pan":
				prefix = customerId + "_P";
				break;
			case "passport":
				prefix = customerId + "_PP";
				break;
			}

			if (files != null) {
				for (File file : files) {
					if (file.getName().startsWith(prefix)) {
						fileDeleted = file.delete();
						if (fileDeleted) {
							// Delete from database as well
							cds.deleteDocFromDatabase(customerId, docType);
							break;
						}
					}
				}
			}

			if (fileDeleted) {
				ApiResponse apiRes = new ApiResponse(HttpStatus.OK.value(), "Document deleted successfully", true);
				return new ResponseEntity<>(apiRes, HttpStatus.OK);
			} else {
				ApiResponse apiRes = new ApiResponse(HttpStatus.NOT_FOUND.value(), "Document file not found", false);
				return new ResponseEntity<>(apiRes, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiRes = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete document",
					e.getMessage());
			return new ResponseEntity<>(apiRes, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/pdf/{id}")
	public ResponseEntity<byte[]> generatePdf(@PathVariable int id) {
		try {
			ByteArrayInputStream bis = ps.generateCustomerPdf(id);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=customer_" + id + ".pdf");

			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bis.readAllBytes());
		} catch (DocumentException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
//	@PostMapping("/auth")
//	public ResponseEntity<ApiResponse> auth(@RequestBody CustomerLoginDto data){
//		CustomerResponseDTO cust = cs.customerAuth(data);
//		ApiResponse res = new ApiResponse(HttpStatus.OK.value(), "loginSuccessful", cust);
//		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
//	}

	@PostMapping("/auth")
	public ResponseEntity<ApiResponse> customerAuth(@RequestBody CustomerLoginDto custAuth) {
		CustomerResponseDTO isAuth = cs.customerAuth(custAuth);
		if (isAuth != null) {
			ApiResponse apiRes = new ApiResponse(HttpStatus.OK.value(), "login Successful", isAuth);
			return new ResponseEntity<ApiResponse>(apiRes, HttpStatus.OK);
		} else {
			ApiResponse apiRes = new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "login failed", isAuth);
			return new ResponseEntity<ApiResponse>(apiRes, HttpStatus.UNAUTHORIZED);
		}
		
	}
}
