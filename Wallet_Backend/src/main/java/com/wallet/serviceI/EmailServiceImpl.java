package com.wallet.serviceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.wallet.model.Email;
import com.wallet.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public String sendSimpleMail(Email details) {
		log.info("req rec");
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());

			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		}

		catch (Exception e) {
			e.printStackTrace();

			return "Error while Sending Mail";
		}
	}

	@Override
	public void LastDayMail(String to, String name, String expiryDate) {
		// TODO Auto-generated method stub
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject("Account Expiry Reminder");
		msg.setText("Hi " + name + ",\n\nYour account is expiring on " + expiryDate
				+ ". Please renew it soon to continue enjoying our services.\n\nBest,\nSupport Team");
		javaMailSender.send(msg);

	}
}
