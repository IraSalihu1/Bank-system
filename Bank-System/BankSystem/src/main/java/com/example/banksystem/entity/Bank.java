package com.example.banksystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bankName;
    private BigDecimal totalTransactionFlatFee;
    private BigDecimal totalTransactionPercentFeeValue;
    private BigDecimal totalTransferAmount;
    private BigDecimal transactionFlatFee;
    private BigDecimal transactionPercentFeeValue;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Account> accounts = new ArrayList<>();

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", bankName='" + bankName + '\'' +
                ", totalTransactionFlatFee=" + totalTransactionFlatFee +
                ", totalTransactionPercentFeeValue=" + totalTransactionPercentFeeValue +
                ", totalTransferAmount=" + totalTransferAmount +
                ", transactionFlatFee=" + transactionFlatFee +
                ", transactionPercentFeeValue=" + transactionPercentFeeValue +
                ", accounts=" + accounts +
                '}';
    }
}
