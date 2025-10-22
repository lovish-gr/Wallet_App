package com.wallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@SequenceGenerator(name = "seqd", sequenceName = "SEQD", initialValue = 1, allocationSize = 2)
public class CustomerDocuments {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqd")
	private int customerDocumentId;
	
	@OneToOne
	private Customer customer;
	
	private String aadhaarFilePath;
	private String panFilePath;
	private String passportFilePath;
	
}
