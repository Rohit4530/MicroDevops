package com.rohit.accounts.controller;

import com.rohit.accounts.constants.AccountConstant;
import com.rohit.accounts.dto.CustomerDto;
import com.rohit.accounts.dto.ResponseDto;
import com.rohit.accounts.service.I_AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "CRUD REST API FOR ACCOUNT IN BANK APPLICATION",
        description = "CRUD REST API FOR CREATE ,UPDATE ,DELETE,GET Account Details"
)
@RestController
@RequestMapping(path = "/api")
@Validated
public class AccountController {
    private  final I_AccountService accountService;
    @Autowired
    public AccountController(I_AccountService accountService) {
        this.accountService = accountService;
    }
   @Operation(
           summary = "Create Account Rest API"
   )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto>createAccount(@Valid @RequestBody CustomerDto dto){
       accountService.createAccount(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        AccountConstant.STATUS_201,
                        AccountConstant.MESSAGE_201
                ));
    }
    @Operation(
            summary = "Fetch Account Rest API"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto>fetchAccountDetails(@RequestParam
                                                              @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digit")
                                                              String mobileNumber){
      CustomerDto customerDto=  accountService.fetchAccount(mobileNumber);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(customerDto);
    }
    @Operation(
            summary = "Update Account Rest API"
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto>updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated=accountService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(new ResponseDto(
                            AccountConstant.STATUS_200,
                            AccountConstant.MESSAGE_200
                    ));
        }else {
           return  ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new ResponseDto(
                           AccountConstant.STATUS_500,
                           AccountConstant.MESSAGE_500
                   ));
        }
    }
    @Operation(
            summary = "Delete Account Rest API"
    )
   @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto>deleteAccountDetails(@RequestParam
                                                              @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digit")
                                                              String mobileNumber){
        boolean isDeleted=accountService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(
                            AccountConstant.STATUS_200,
                            AccountConstant.MESSAGE_200
                    ));
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(
                            AccountConstant.STATUS_500,
                            AccountConstant.MESSAGE_500
                    ));
        }
   }
}
