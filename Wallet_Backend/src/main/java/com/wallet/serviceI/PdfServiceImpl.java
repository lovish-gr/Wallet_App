package com.wallet.serviceI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;
import com.wallet.model.Account;
import com.wallet.model.Address;
import com.wallet.model.Customer;
import com.wallet.model.Transaction;
import com.wallet.repo.CustomerRepo;
import com.wallet.service.PdfService;


@Service
public class PdfServiceImpl implements PdfService{
	
	@Autowired
	CustomerRepo cr;
	
	
	public ByteArrayInputStream generateCustomerPdf(int customerId) throws DocumentException {
        Customer customer = cr.findByCustomerId(customerId);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        document.add(new Paragraph("Customer Information Report", titleFont));
        document.add(new Paragraph(" "));

        // Basic Details
        document.add(new Paragraph("Name: " + customer.getFirstName()));
        document.add(new Paragraph("Email: " + customer.getEmailId()));
        document.add(new Paragraph("Phone: " + customer.getContactNo()));
        document.add(new Paragraph(" "));

        // Address Details
        if (customer.getAddress() != null) {
            Address a = customer.getAddress();
            document.add(new Paragraph("Address: " + a.getAddressLine1() + ", " + a.getCity() + ", " + a.getState() + " - " + a.getPincode()));
            document.add(new Paragraph(" "));
        }

        // Account Details
        if (customer.getAccoutns() != null) {
            List<Account> acc = customer.getAccoutns();
            for(Account ac : acc) {
            document.add(new Paragraph("Account Number: " + ac.getAccountNumber()));
            document.add(new Paragraph("Account Type: " + ac.getAccType()));
            document.add(new Paragraph("Balance: ₹" + ac.getOpeningBalance()));
            document.add(new Paragraph(" "));
            }
        }

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
