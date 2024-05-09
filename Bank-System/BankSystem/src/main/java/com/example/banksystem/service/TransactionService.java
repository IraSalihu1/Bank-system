package com.example.banksystem.service;

import com.example.banksystem.entity.Transaction;
import com.example.banksystem.response.CustomResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    public CustomResponse getTransactionsPerAccount(Long accountId);

    public CustomResponse transferMoney(Long fromId, Long toId, BigDecimal amount, boolean isFlatFee, String reason) throws Exception;

    public CustomResponse withdrawMoney(Long accountId, BigDecimal amount, boolean isFlatFee) throws Exception;

    public CustomResponse depositMoney(Long accountId, BigDecimal amount, boolean isFlatFee) throws Exception;
}
