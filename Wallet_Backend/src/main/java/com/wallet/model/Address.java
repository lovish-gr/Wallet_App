package com.wallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "seq1", sequenceName = "SEQ1", initialValue = 1, allocationSize = 2)
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
	int addressId;
	@NotBlank
	String addressLine1;
	@NotBlank
	String addressLine2;
	@NotBlank
	String city;
	@NotBlank
	String state;
	@NotBlank
	String pincode;
}
