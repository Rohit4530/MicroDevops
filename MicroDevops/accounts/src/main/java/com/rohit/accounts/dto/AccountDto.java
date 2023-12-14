package com.rohit.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Account"
)
public class AccountDto {
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digit")
    @NotEmpty(message = "Account Number cannot be empty")
    private Long accountNumber;
    @NotEmpty(message = "Account type cannot be empty")
    private String accountType;
    @NotEmpty(message = "Branch address cannot be empty")
    private String branchAddress;
}
