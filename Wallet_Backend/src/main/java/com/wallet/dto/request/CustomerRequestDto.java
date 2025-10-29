package com.wallet.dto.request;

import com.wallet.model.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String emailId;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "ContactNo must be 10 digits")
    private String contactNo;

    @NotNull
    private Gender gender;

    @NotBlank
    @Size(min = 8, message = "Password must be exactly 8 characters")
    private String password;

    @NotBlank
    private String addressLine1;

    @NotBlank
    private String addressLine2;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String pincode;
}

