package com.rohit.accounts.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
