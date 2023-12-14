package com.rohit.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Customer"
)

public class CustomerDto{
    @NotEmpty(message = "Name cannot be empty")
    @Size(min=5,max = 20,message = "Name should be in range of 5 to 20")
    private String name;
    @NotEmpty(message = "Mail address cannot be empty")
    @Email(message = "Email address should be a valid value")
    private String email;
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digit")
    private  String mobileNumber;
    private AccountDto accountDto;
}
