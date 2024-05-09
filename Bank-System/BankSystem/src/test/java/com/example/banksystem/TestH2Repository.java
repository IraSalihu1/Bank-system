package com.example.banksystem;

import com.example.banksystem.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<Bank, Long> {
}
