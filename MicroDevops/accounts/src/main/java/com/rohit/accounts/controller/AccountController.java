package com.rohit.accounts.controller;

import com.rohit.accounts.constants.AccountConstant;
import com.rohit.accounts.dto.CustomerDto;
import com.rohit.accounts.dto.ResponseDto;
import com.rohit.accounts.service.I_AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class AccountController {
    private  final I_AccountService accountService;
    @Autowired
    public AccountController(I_AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto>createAccount(@RequestBody CustomerDto dto){
       accountService.createAccount(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        AccountConstant.STATUS_201,
                        AccountConstant.MESSAGE_201
                ));
    }
}
