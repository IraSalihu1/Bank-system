package com.example.banksystem.service;

import com.example.banksystem.entity.Account;
import com.example.banksystem.response.CustomResponse;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    public CustomResponse getAllAccountsByBankId(Long bankId);

    public CustomResponse createAccount(String name, BigDecimal initialBalance, String bankName);

    public Account getAccountById(Long accountId) throws Exception;

    public CustomResponse getAccountBalanceByAccountId(Long accountId);

/*    public void saveAccount(Account account);*/
}
