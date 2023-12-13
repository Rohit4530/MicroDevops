package com.rohit.accounts.service;

import com.rohit.accounts.dto.CustomerDto;

public interface  I_AccountService {
    void createAccount(CustomerDto customerDto);
    CustomerDto fetchAccount(String mobileNumber);
    boolean updateAccount(CustomerDto dto);
    boolean deleteAccount(String mobileNumber);
}
