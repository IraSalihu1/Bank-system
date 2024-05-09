package com.example.banksystem.serviceimpl;

import com.example.banksystem.entity.Account;
import com.example.banksystem.entity.Bank;
import com.example.banksystem.entity.Transaction;
import com.example.banksystem.repository.AccountRepository;
import com.example.banksystem.repository.BankRepository;
import com.example.banksystem.repository.TransactionRepository;
import com.example.banksystem.response.CustomResponse;
import com.example.banksystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public CustomResponse getTransactionsPerAccount(Long accountId) {
        if (transactionRepository.findTransactionByAccountId(accountId).isEmpty())
            return CustomResponse.builder().data(Collections.emptyList()).message("Zero transactions from this account!").code(204).build();
       else
            return CustomResponse.builder().data( transactionRepository.findTransactionByAccountId(accountId)).message("Success").code(200).build();
    }

    @Override
    public CustomResponse transferMoney(Long fromId, Long toId, BigDecimal amount, boolean isFlatFee, String reason) throws Exception {
        Account fromAccount = accountService.getAccountById(fromId);
        Account toAccount = accountService.getAccountById(toId);
        //Optional<Bank> bank = bankRepository.findById(fromAccount.getBank().getId());
        BigDecimal fee = calculateFee(fromAccount.getBank().getId(), amount, isFlatFee);

        if (fromAccount.getBalance().compareTo(amount.add(fee)) < 0) {
            return CustomResponse.builder().message("Not enough funds").data(fromAccount).code(406).build();
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount.add(fee)));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setOriginAccountId(fromAccount.getId());
        transaction.setResultingAccountId(toAccount.getId());
        transaction.setTransactionReason(reason);
        fromAccount.getBank().setTotalTransferAmount(fromAccount.getBank().getTotalTransferAmount().add(amount));
       // bankRepository.save(fromAccount.getBank());

        /*Optional<Bank> bank = bankRepository.findById(fromAccount.getBank().getId());*/

        if (isFlatFee) {
            fromAccount.getBank().setTotalTransactionFlatFee(fromAccount.getBank().getTotalTransactionFlatFee().add(fee));
        } else {
            fromAccount.getBank().setTotalTransactionPercentFeeValue(fromAccount.getBank().getTotalTransactionPercentFeeValue().add(fee));
        }
        bankRepository.save(fromAccount.getBank());

        return CustomResponse.builder().data(transactionRepository.save(transaction)).message("Success").code(200).build();
    }

    @Override
    public CustomResponse withdrawMoney(Long accountId, BigDecimal amount, boolean isFlatFee) throws Exception {
        Account account = accountService.getAccountById(accountId);
        BigDecimal fee = calculateFee(account.getBank().getId(), amount, isFlatFee);
        if (account.getBalance().compareTo(amount.add(fee)) < 0) {
            return CustomResponse.builder().message("Not enough funds").data(account).code(406).build();
        }
        account.setBalance(account.getBalance().subtract(amount.add(fee)));
        accountRepository.save(account);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setOriginAccountId(account.getId());
        transaction.setTransactionReason("Withdraw");
        account.getBank().setTotalTransferAmount(account.getBank().getTotalTransferAmount().add(amount));

        if (isFlatFee) {
            account.getBank().setTotalTransactionFlatFee(account.getBank().getTotalTransactionFlatFee().add(fee));
        } else {
            account.getBank().setTotalTransactionPercentFeeValue(account.getBank().getTotalTransactionPercentFeeValue().add(fee));
        }
        bankRepository.save(account.getBank());
        return CustomResponse.builder().data(transactionRepository.save(transaction)).message("Success").code(200).build();
    }

    @Override
    public CustomResponse depositMoney(Long accountId, BigDecimal amount, boolean isFlatFee) throws Exception {
        Account account = accountService.getAccountById(accountId);
        BigDecimal fee = calculateFee(account.getBank().getId(), amount, isFlatFee);
        if (account.getBalance().add(amount).compareTo(fee) < 0) {
            return CustomResponse.builder().message("Not enough funds").data(account).code(406).build();
        }
        account.setBalance(account.getBalance().add(amount.subtract(fee)));
        accountRepository.save(account);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setOriginAccountId(account.getId());
        transaction.setTransactionReason("deposit");
        account.getBank().setTotalTransferAmount(account.getBank().getTotalTransferAmount().add(amount));

       // Optional<Bank> bank = bankRepository.findById(account.getBank().getId());

        if (isFlatFee) {
            account.getBank().setTotalTransactionFlatFee(account.getBank().getTotalTransactionFlatFee().add(fee));
        } else {
            account.getBank().setTotalTransactionPercentFeeValue(account.getBank().getTotalTransactionPercentFeeValue().add(fee));
        }
        bankRepository.save(account.getBank());

        return CustomResponse.builder().data(transactionRepository.save(transaction)).message("Success").code(200).build();
    }

    private BigDecimal calculateFee(Long bankId, BigDecimal amount, boolean isFlatFee) {
        Optional<Bank> bank = bankRepository.findById(bankId);
        return isFlatFee ? bank.get().getTransactionFlatFee() : amount.multiply(bank.get().getTransactionPercentFeeValue().divide(new BigDecimal(100)));
    }
}
