package com.rohit.accounts.service;

import com.rohit.accounts.constants.AccountConstant;
import com.rohit.accounts.dto.AccountDto;
import com.rohit.accounts.dto.CustomerDto;
import com.rohit.accounts.entity.Account;
import com.rohit.accounts.entity.Customer;
import com.rohit.accounts.exception.CustomerAlreadyExistException;
import com.rohit.accounts.exception.ResourceNotFoundException;
import com.rohit.accounts.mapper.AccountMapper;
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
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","Mobile No",mobileNumber)
        );
        Account account= accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","CustomerId",customer.getCustomerId().toString())
        );
      CustomerDto customerDto= CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
      customerDto.setAccountDto(AccountMapper.mapToAccountDto(account,new AccountDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto dto) {
        boolean isUpdated=false;
        AccountDto accountDto=dto.getAccountDto();
        if(accountDto!=null){
            Account account=accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    ()->new ResourceNotFoundException("Account","Account Number",accountDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccount(accountDto,account);
            account=accountRepository.save(account);
            Long customerId=account.getCustomerId();
            Customer customer=customerRepository.findById(customerId).orElseThrow(
                    ()->new ResourceNotFoundException("Customer","CustomerId",customerId.toString())
            );
            CustomerMapper.mapToCustomer(dto,customer);
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","Mobile Number",mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
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
