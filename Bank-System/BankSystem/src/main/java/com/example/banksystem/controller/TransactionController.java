package com.example.banksystem.controller;

import com.example.banksystem.response.CustomResponse;
import com.example.banksystem.serviceimpl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionServiceImpl transactionService;

    @GetMapping("/getTransactionByAccountId/accountId/{accountId}")
    public CustomResponse getTransactionByAccountId(@PathVariable Long accountId) {
        return transactionService.getTransactionsPerAccount(accountId);
    }

    @PostMapping("/transaction/{fromId}/{toId}/{amount}/{isFlatFee}/{reason}")
    public CustomResponse transferMoney(@PathVariable Long fromId,@PathVariable Long toId,@PathVariable BigDecimal amount,@PathVariable boolean isFlatFee,@PathVariable String reason) throws Exception {
        return transactionService.transferMoney(fromId, toId, amount, isFlatFee, reason);
    }

    @PostMapping("/withdraw/{fromId}/{amount}/{isFlatFee}")
    public CustomResponse withdrawMoney(@PathVariable Long fromId, @PathVariable BigDecimal amount, @PathVariable boolean isFlatFee) throws Exception {
        return transactionService.withdrawMoney(fromId, amount, isFlatFee);
    }

    @PostMapping("/deposit/{fromId}/{amount}/{isFlatFee}")
    public CustomResponse depositMoney(@PathVariable Long fromId, @PathVariable BigDecimal amount, @PathVariable boolean isFlatFee) throws Exception {
        return transactionService.depositMoney(fromId, amount, isFlatFee);
    }
}
