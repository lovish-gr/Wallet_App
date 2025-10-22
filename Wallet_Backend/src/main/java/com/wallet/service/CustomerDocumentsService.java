package com.wallet.service;

import com.wallet.model.CustomerDocuments;

public interface CustomerDocumentsService {
	
	public int newDoc(int custId, String aadhaarFilePath,String panFilePath,String passportFilePath);
	public CustomerDocuments getDoc(int custId);
	public void deleteDocument(int customerId, String fieldName);
	public void deleteDocFromDatabase(int customerId, String docType);
}
