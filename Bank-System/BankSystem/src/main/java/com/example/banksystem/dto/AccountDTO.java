package com.example.banksystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
public class AccountDTO {
    private String name;
    private BigDecimal balance;
    private String bankName;
}
