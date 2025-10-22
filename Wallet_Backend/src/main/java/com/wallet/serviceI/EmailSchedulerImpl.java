package com.wallet.serviceI;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.wallet.model.Email;
import com.wallet.service.EmailScheduler;
import com.wallet.service.EmailService;

@Service
public class EmailSchedulerImpl implements EmailScheduler{
	
	@Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private EmailService emailService;

    @Override
    public void scheduleEmail(Email email) {
    	
    	System.out.println("mail scheduled");
        Instant sendTime = Instant.now().plusSeconds(3600);

        taskScheduler.schedule(() -> {
            emailService.sendSimpleMail(email);
        }, Date.from(sendTime));
    }
	

}
