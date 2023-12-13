package com.rohit.accounts.mapper;

import com.rohit.accounts.dto.CustomerDto;
import com.rohit.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer,CustomerDto dto){
     dto.setEmail(customer.getEmail());
     dto.setName(customer.getName());
     dto.setMobileNumber(customer.getMobileNumber());
     return dto;
    }
    public static Customer mapToCustomer(CustomerDto dto,Customer customer){
        customer.setEmail(dto.getEmail());
        customer.setName(dto.getName());
        customer.setMobileNumber(dto.getMobileNumber());
        return customer;
    }
}
