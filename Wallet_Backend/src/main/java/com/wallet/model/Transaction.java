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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "seqt", sequenceName = "SEQT", initialValue = 1, allocationSize = 2)
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqt")
	private int transaction_id;
	
	private double amount;
	
	private String description;
	
	private LocalDate transaction_date;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transaction_type;
	
	@ManyToOne
	@JoinColumn(name = "frm_accfk")
	private Account frmaccfk;
	
	@ManyToOne
	@JoinColumn(name = "to_accfk")
	private Account  toaccfk ;
	
}
