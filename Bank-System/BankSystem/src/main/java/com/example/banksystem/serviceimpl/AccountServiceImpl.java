package com.example.banksystem.serviceimpl;

import com.example.banksystem.entity.Account;
import com.example.banksystem.entity.Bank;
import com.example.banksystem.repository.AccountRepository;
import com.example.banksystem.repository.BankRepository;
import com.example.banksystem.response.CustomResponse;
import com.example.banksystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public CustomResponse getAllAccountsByBankId(Long bankId) {
        Optional<Bank> bank = bankRepository.findById(bankId);
        if (bank.isPresent()) {
            if (accountRepository.findBankAccountsByBankId(bankId).isEmpty())
                return CustomResponse.builder().data(Collections.EMPTY_LIST).message("No accounts found!").code(404).build();
            else
                return CustomResponse.builder().data(accountRepository.findBankAccountsByBankId(bankId)).code(200).message("Success").build();
        }else {
            return CustomResponse.builder().data(Collections.EMPTY_LIST).message("Bank not found!").code(404).build();
        }
    }

    @Override
    public CustomResponse createAccount(String name, BigDecimal initialBalance, String bankName) {
        Optional<Bank> bank = bankRepository.findByBankName(bankName);
        if (bank.isPresent()) {
            Account account = Account.builder().name(name).balance(initialBalance).bank(bank.get()).build();
            bank.get().getAccounts().add(account);
            bankRepository.save(bank.get());
            return CustomResponse.builder().data(account).code(200).message("Account created").build();
        } else {
            return CustomResponse.builder().code(404).message("Bank not found!").build();
        }
    }

    @Override
    public Account getAccountById(Long accountId) throws Exception {
        return accountRepository.findById(accountId).orElseThrow(() -> new Exception("Account not found"));
    }

    @Override
    public CustomResponse getAccountBalanceByAccountId(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent())
            return CustomResponse.builder().data(accountRepository.findBalanceByAccountId(accountId)).message("Success").code(200).build();
        else
            return CustomResponse.builder().message("Account not found!").code(404).build();
    }

/*    @Override
    public void saveAccount(Account account) {
        Account newAccount = Account.builder().name(account.getName()).balance(account.getBalance())
                .bank(account.getBank()).transactions(!account.getTransactions().isEmpty() ? account.getTransactions() : Collections.emptyList()).build();
        accountRepository.save(newAccount);
    }*/
}
