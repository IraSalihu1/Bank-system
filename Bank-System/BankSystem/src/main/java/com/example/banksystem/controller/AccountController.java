package com.example.banksystem.controller;

import com.example.banksystem.dto.AccountDTO;
import com.example.banksystem.entity.Account;
import com.example.banksystem.response.CustomResponse;
import com.example.banksystem.serviceimpl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("/getListOfBankAccounts/bankId/{bankId}")
    public CustomResponse getAllBankAccounts(@PathVariable Long bankId) {
        return accountService.getAllAccountsByBankId(bankId);
    }

    @GetMapping("/getAccountBalanceByAccountId/accountId/{accountId}")
    public CustomResponse getAccountBalanceByAccountId(@PathVariable Long accountId) {
        return accountService.getAccountBalanceByAccountId(accountId);
    }

    @PostMapping("/createAccount")
    public CustomResponse createAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO.getName(),accountDTO.getBalance(), accountDTO.getBankName());
    }

}
