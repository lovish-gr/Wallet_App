package com.wallet.serviceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.model.Customer;
import com.wallet.model.CustomerDocuments;
import com.wallet.repo.CustomerDocumentsRepo;
import com.wallet.repo.CustomerRepo;
import com.wallet.service.CustomerDocumentsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerDocumentsServiceImpl implements CustomerDocumentsService {

	@Autowired
	CustomerDocumentsRepo cdr;

	private final CustomerRepo cr;

	@Override
	public int newDoc(int custId, String aadhaarFilePath, String panFilePath, String passportFilePath) {
		// TODO Auto-generated method stub
		Customer cust = cr.findByCustomerId(custId);
		CustomerDocuments newCustDoc = CustomerDocuments.builder().customer(cust).aadhaarFilePath(aadhaarFilePath)
				.panFilePath(panFilePath).passportFilePath(passportFilePath).build();

		return cdr.save(newCustDoc).getCustomerDocumentId();
	}

	@Override
	public void deleteDocument(int customerId, String fieldName) {
		// TODO Auto-generated method stub
		CustomerDocuments customerDoc = cdr.findByCustomerCustomerId(customerId);
		if (customerDoc != null) {

			if (fieldName.equals("aadhaarFilePath")) {
				customerDoc.setAadhaarFilePath(null);
			} else if (fieldName.equals("panFilePath")) {
				customerDoc.setPanFilePath(null);
			} else if (fieldName.equals("passportFilePath")) {
				customerDoc.setPassportFilePath(null);
			}

			cdr.save(customerDoc);
		}

	}

	public void deleteDocFromDatabase(int customerId, String docType) {
		try {
			switch (docType) {
			case "aadhaar":
				deleteDocument(customerId, "aadhaarFilePath");
				break;
			case "pan":
				deleteDocument(customerId, "panFilePath");
				break;
			case "passport":
				deleteDocument(customerId, "passportFilePath");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error deleting from database: " + e.getMessage());
		}
	}

	@Override
	public CustomerDocuments getDoc(int custId) {
		// TODO Auto-generated method stub
		return cdr.findByCustomerCustomerId(custId);
	}

}
