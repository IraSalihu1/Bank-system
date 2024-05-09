package com.example.banksystem;


import com.example.banksystem.entity.Account;
import com.example.banksystem.entity.Bank;
import com.example.banksystem.repository.BankRepository;
import com.example.banksystem.response.CustomResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankSystemApplicationTests {

    @LocalServerPort
    private int port;

    private String baseUrl="http://localhost";

    private static RestTemplate restTemplate;

    @BeforeAll
    private static void init(){
        restTemplate = new RestTemplate();
    }

    @Autowired
    private TestH2Repository testH2Repository;


    @BeforeEach
    public void setUp(){
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/bank");
    }

    @Test
    public  void testAddBank(){
        List<Account> accounts = new ArrayList<>();
        Bank bank = new Bank(1l,"teb",new BigDecimal(1),new BigDecimal(1),new BigDecimal(1),new BigDecimal(1),new BigDecimal(1),accounts);
        CustomResponse response = restTemplate.postForObject(baseUrl.concat("/createBank"),bank, CustomResponse.class);
        assertEquals(1,testH2Repository.findAll().size());
        assertEquals(bank.getBankName(),testH2Repository.findAll().get(0).getBankName());
    }

    @Test
    public  void  testGetTotalTransactionFlatFee(){
        List<Account> accounts = new ArrayList<>();
        Bank bank = new Bank(1l,"teb",new BigDecimal(1),new BigDecimal(1),new BigDecimal(1),new BigDecimal(1),new BigDecimal(1),accounts);
        restTemplate.postForObject(baseUrl.concat("/createBank"),bank, CustomResponse.class);
        CustomResponse response2 = restTemplate.getForObject(baseUrl.concat("/getTotalTransactionFlatFee/bankId").concat("/{bankId}"),CustomResponse.class,1);
        assertEquals("Success",response2.getMessage());
    }

}
