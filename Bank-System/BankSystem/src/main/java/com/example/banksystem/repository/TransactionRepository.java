package com.example.banksystem.repository;

import com.example.banksystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.originAccountId=:accountId")
    List<Transaction> findTransactionByAccountId(Long accountId);

}
