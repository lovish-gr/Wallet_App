package com.wallet.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "seq", sequenceName = "SEQ", initialValue = 1, allocationSize = 2)
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	int customerId;
	String firstName;
	String lastName;
	String emailId;
	String contactNo;
	
	@OneToOne
	@JoinColumn(name="addressFk")
	Address address;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	String password;
	private LocalDate registrationDate;
	private LocalDate lastTrailDate;
	
	@OneToMany(targetEntity = Account.class, mappedBy = "customer")
	private List<Account> accoutns=new ArrayList<>();
	
	
	
	
}
