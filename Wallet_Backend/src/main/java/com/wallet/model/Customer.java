package com.wallet.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
	private int customerId;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	@jakarta.validation.constraints.Email
	private String emailId;

	@NotBlank
	@Pattern(regexp = "\\d{10}", message = "ContactNo must be 10 digits")
	private String contactNo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "addressFk")
	@Valid
	private Address address;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Gender gender;

	@NotBlank
	@Size(min = 8, message = "Password must be exactly 8 characters")
	private String password;
	private LocalDate registrationDate;
	private LocalDate lastTrailDate;

	@OneToMany(targetEntity = Account.class, mappedBy = "customer")
	private List<Account> accoutns = new ArrayList<>();

}
