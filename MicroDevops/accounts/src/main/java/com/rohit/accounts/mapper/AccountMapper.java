package com.rohit.accounts.mapper;

import com.rohit.accounts.dto.AccountDto;
import com.rohit.accounts.entity.Account;

public class AccountMapper {
    public static AccountDto mapToAccountDto(Account account, AccountDto dto){
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountType(account.getAccountType());
        dto.setBranchAddress(account.getBranchAddress());
        return  dto;
    }
    public static Account mapToAccount( AccountDto dto,Account account){
        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountType(dto.getAccountType());
        account.setBranchAddress(dto.getBranchAddress());
        return  account;
    }
}
