package com.example.banksystem.repository;

import com.example.banksystem.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    @Query("select b.totalTransactionFlatFee from Bank b where b.id=:bankId")
    BigDecimal findTotalTransactionFlatFee(Long bankId);

    @Query("select b.totalTransactionPercentFeeValue from Bank b where b.id=:bankId")
    BigDecimal findTotalTransactionPercentFeeValue(Long bankId);

    Optional<Bank> findByBankName(String bankName);
}
