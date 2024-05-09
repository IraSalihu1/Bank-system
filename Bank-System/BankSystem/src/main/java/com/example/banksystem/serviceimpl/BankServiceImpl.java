package com.example.banksystem.serviceimpl;

import com.example.banksystem.entity.Bank;
import com.example.banksystem.repository.BankRepository;
import com.example.banksystem.repository.TransactionRepository;
import com.example.banksystem.response.CustomResponse;
import com.example.banksystem.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public CustomResponse getTotalTransactionFlatFee(Long bankId) {
        if (bankRepository.findById(bankId).isPresent())
            return CustomResponse.builder().data(bankRepository.findTotalTransactionFlatFee(bankId)).code(200).message("Success").build();
        else
            return CustomResponse.builder().code(404).message("Bank not found").build();
    }

    @Override
    public CustomResponse getTotalTransactionPercentFeeValue(Long bankId) {
        if (bankRepository.findById(bankId).isPresent())
            return CustomResponse.builder().data(bankRepository.findTotalTransactionPercentFeeValue(bankId)).code(200).message("Success").build();
        else
            return CustomResponse.builder().code(404).message("Bank not found").build();
    }

    @Override
    public CustomResponse createBank(String name, BigDecimal totalFlatFee, BigDecimal  totalPercentFee, BigDecimal transactionalFlatFee, BigDecimal transactionPercentFeeValue) {
        Bank bank = new Bank();
        bank.setBankName(name);
        bank.setTotalTransactionFlatFee(totalFlatFee);
        bank.setTotalTransactionPercentFeeValue(totalPercentFee);
        bank.setTransactionFlatFee(transactionalFlatFee);
        bank.setTransactionPercentFeeValue(transactionPercentFeeValue);
        bank.setTotalTransferAmount(BigDecimal.ZERO);
        return CustomResponse.builder().data(bankRepository.save(bank)).message("Success").code(200).build();
    }


    // Additional methods for withdrawing, depositing, and viewing accounts
}
