package com.wallet.model;

import java.time.LocalDate;



import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@SequenceGenerator(name = "seq1", sequenceName = "SEQ1", initialValue = 101, allocationSize = 2)
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
	private int accountNumber;
	@ManyToOne
//	@JoinColumn(name = "cutomerFk")
	private Customer customer;
	@Enumerated(EnumType.STRING)
	private AccountType accType;
	
	private double openingBalance;
	private LocalDate openingDate;
	private String description;
	
}
