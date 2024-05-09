package com.example.banksystem.controller;

import com.example.banksystem.dto.BankDTO;
import com.example.banksystem.response.CustomResponse;
import com.example.banksystem.serviceimpl.AccountServiceImpl;
import com.example.banksystem.serviceimpl.BankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
public class BankController {
    @Autowired
    private BankServiceImpl bankService;
    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("/getTotalTransactionPercentFeeValue/bankId/{bankId}")
    public CustomResponse getTotalTransactionPercentFeeValue(@PathVariable Long bankId) {
        return bankService.getTotalTransactionPercentFeeValue(bankId);
    }

    @GetMapping("/getTotalTransactionFlatFee/bankId/{bankId}")
    public CustomResponse getTotalTransactionFlatFee(@PathVariable Long bankId) {
        return bankService.getTotalTransactionFlatFee(bankId);
    }

    @PostMapping("/createBank")
    public CustomResponse createBank(@RequestBody BankDTO bankDTO) {
        return bankService.createBank(bankDTO.getBankName(), bankDTO.getTotalTransactionFlatFee(), bankDTO.getTotalTransactionPercentFeeValue(),
                bankDTO.getTransactionFlatFee(), bankDTO.getTransactionPercentFeeValue());
    }
}
