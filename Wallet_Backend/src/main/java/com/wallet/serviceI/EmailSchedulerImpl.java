package com.wallet.serviceI;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wallet.model.Customer;
import com.wallet.model.Email;
import com.wallet.repo.CustomerRepo;
import com.wallet.service.EmailScheduler;
import com.wallet.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties(prefix = "app.settings")
@Service
@Slf4j
public class EmailSchedulerImpl implements EmailScheduler {

	@Autowired
	TaskScheduler taskScheduler;

	@Autowired
	CustomerRepo repository;

	@Autowired
	EmailService emailService;

	@Value("${app.settings.time}")
	private long time;


	@Override
	public void scheduleWelcomeEmail(Email email) {

		Instant sendTime = Instant.now().plusSeconds(time);

	
		log.info("mail scheduled" + time);
		taskScheduler.schedule(() -> {
			emailService.sendSimpleMail(email);
		}, Date.from(sendTime));
	}

	@Override
	@Scheduled(cron = "0 0 0 * * *")
	public void scheduleLastDayMail() {
		// TODO Auto-generated method stub
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		log.info("expiry mail scheduld");

		List<Customer> customers = repository.findCustomersExpiringTodayOrTomorrow(today, tomorrow);
		for (Customer customer : customers) {

			emailService.LastDayMail(customer.getEmailId(), customer.getFirstName(),
					customer.getLastTrailDate().toString());
			log.info(" Sent expiry reminder to: " + customer.getEmailId());
		}

	}

}
