package com.wallet.service;

import java.io.ByteArrayInputStream;

import com.itextpdf.text.DocumentException;

public interface PdfService {
	
	public ByteArrayInputStream generateCustomerPdf(int customerId) throws DocumentException;
}
