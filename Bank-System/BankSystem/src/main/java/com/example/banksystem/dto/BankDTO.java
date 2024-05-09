package com.example.banksystem.dto;

import lombok.Getter;

import java.math.BigDecimal;
@Getter
public class BankDTO {
    private String bankName;
    private BigDecimal totalTransactionFlatFee;
    private BigDecimal totalTransactionPercentFeeValue;
    private BigDecimal totalTransferAmount;
    private BigDecimal transactionFlatFee;
    private BigDecimal transactionPercentFeeValue;
}
