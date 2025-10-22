package com.wallet.serviceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.wallet.model.Email;
import com.wallet.service.EmailService;


@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired private JavaMailSender javaMailSender;
    public String sendSimpleMail(Email details)
    {
    	System.out.println("req rec");
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

      
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
        	e.printStackTrace();
        	
            return "Error while Sending Mail";
        }
    }
}
