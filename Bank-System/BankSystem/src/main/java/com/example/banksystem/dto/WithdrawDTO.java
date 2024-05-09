package com.example.banksystem.dto;

import lombok.Getter;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
@Getter
public class WithdrawDTO {
    Long fromId;
    BigDecimal amount;
    boolean isFlatFee;
}
