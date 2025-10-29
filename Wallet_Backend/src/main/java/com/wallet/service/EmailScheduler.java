package com.wallet.service;

import com.wallet.model.Email;

public interface EmailScheduler {
	
	public void scheduleWelcomeEmail(Email email);
	
	public void scheduleLastDayMail();
}
