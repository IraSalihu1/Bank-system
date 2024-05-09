package com.example.banksystem.repository;

import com.example.banksystem.entity.Account;
import com.example.banksystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select a.balance from Account a where a.id=:accountId")
    BigDecimal findBalanceByAccountId(Long accountId);

    @Query("select a from Account a where a.bank.id=:bankId")
    List<Account> findBankAccountsByBankId(@PathVariable Long bankId);

}
