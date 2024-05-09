package com.example.banksystem.service;

import com.example.banksystem.entity.Bank;
import com.example.banksystem.response.CustomResponse;

import java.math.BigDecimal;

public interface BankService {
    public CustomResponse getTotalTransactionFlatFee(Long bankId);

    public CustomResponse getTotalTransactionPercentFeeValue(Long bankId);

    public CustomResponse createBank(String name, BigDecimal flatFee, BigDecimal percentFee, BigDecimal transactionalFlatFee, BigDecimal transactionPercentFeeValue);
}
