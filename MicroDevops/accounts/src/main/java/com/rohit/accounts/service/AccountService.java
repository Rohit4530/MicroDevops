package com.rohit.accounts.service;

import com.rohit.accounts.constants.AccountConstant;
import com.rohit.accounts.dto.CustomerDto;
import com.rohit.accounts.entity.Account;
import com.rohit.accounts.entity.Customer;
import com.rohit.accounts.exception.CustomerAlreadyExistException;
import com.rohit.accounts.mapper.CustomerMapper;
import com.rohit.accounts.repository.AccountRepository;
import com.rohit.accounts.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
@Service
public class AccountService implements I_AccountService{
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
       Optional<Customer>optionalCustomer= customerRepository.findByMobileNumber(customerDto.getMobileNumber());
       if(optionalCustomer.isPresent()){
           throw new CustomerAlreadyExistException("Customer already register with mobile number: "+customerDto.getMobileNumber());
       }
       customer.setCreatedAt(LocalDateTime.now());
       customer.setCreatedBy("Anonymous");
        Customer savedCustomer=customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));

    }
    private Account createNewAccount(Customer customer){
        Account newAccount=new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccountNumber=1000000000L+new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setAccountType(AccountConstant.SAVING);
        newAccount.setBranchAddress(AccountConstant.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }

}
