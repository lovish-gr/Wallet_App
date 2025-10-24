package com.wallet.service;

import com.wallet.model.Email;

//Importing required classes


//Interface
public interface EmailService {

 // Method
 // To send a simple email
	String sendSimpleMail(Email details);
	public void LastDayMail(String to, String name,String expiryDate);
}
