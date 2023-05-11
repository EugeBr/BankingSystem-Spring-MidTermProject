package com.ironhack.bankingSystem.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.controller.dto.AccountBalanceDto;
import com.ironhack.bankingSystem.model.*;
import com.ironhack.bankingSystem.repository.AccountHolderRepository;
import com.ironhack.bankingSystem.repository.AddressRepository;
import com.ironhack.bankingSystem.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.ironhack.bankingSystem.model.enums.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllAccounts_validRequest_allAccounts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/admins/accounts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        assertTrue(mvcResult.getResponse().getContentAsString().contains("SAVINGS ACCOUNT"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("CREDIT CARD"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("CHECKING ACCOUNT"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("STUDENT CHECKING ACCOUNT"));
    }

    @Test
    void getAccountById_validId_correctAccount() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/admins/accounts/6"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        assertTrue(mvcResult.getResponse().getContentAsString().contains("SAVINGS ACCOUNT"));
    }

    @Test
    void getAccountById_invalidId_NotFound() throws Exception {
        mockMvc.perform(get("/api/admins/44").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void getAllThirdPartyUsers_validRequest_thirdPartyUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/admins/third-party-users"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Timothy D. Lopez"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Anthony L. Pattison"));
    }

    @Test
    void saveCheckingAccount_validChecking_accountSaved() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Admin admin = new Admin("George L. Lucas");
        adminRepository.save(admin);
        Address address1 = new Address("James Street", "739");
        addressRepository.save(address1);
        LocalDate date1 = LocalDate.of(1946, 2, 18);
        AccountHolder accountHolder1 = new AccountHolder("Rebecca T. Carroll", date1, address1);
        accountHolderRepository.save(accountHolder1);
        Checking checking1 = new Checking(new Money(new BigDecimal(30000)), accountHolder1, admin, ACTIVE, "IJH465D");

        String body = objectMapper.writeValueAsString(checking1);

        mockMvc.perform(post("/api/admins/accounts/checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/api/admins/accounts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Rebecca T. Carroll"));

        mockMvc.perform(delete("/api/admins/accounts/11"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void saveSavingsAccount_validSavings_accountSaved() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Admin admin = new Admin("George L. Lucas");
        adminRepository.save(admin);
        Address address1 = new Address("James Street", "739");
        addressRepository.save(address1);
        LocalDate date1 = LocalDate.of(1946, 2, 18);
        AccountHolder accountHolder1 = new AccountHolder("Rebecca T. Carroll", date1, address1);
        accountHolderRepository.save(accountHolder1);
        Savings savings1 = new Savings(new Money(new BigDecimal(30000)), accountHolder1, admin, ACTIVE, "IJH465D");

        String body = objectMapper.writeValueAsString(savings1);

        mockMvc.perform(post("/api/admins/accounts/savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/api/admins/accounts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Rebecca T. Carroll"));

        mockMvc.perform(delete("/api/admins/accounts/11"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void saveCreditCardAccount() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Admin admin = new Admin("George L. Lucas");
        adminRepository.save(admin);
        Address address1 = new Address("James Street", "739");
        addressRepository.save(address1);
        LocalDate date1 = LocalDate.of(1946, 2, 18);
        AccountHolder accountHolder1 = new AccountHolder("Rebecca T. Carroll", date1, address1);
        accountHolderRepository.save(accountHolder1);
        CreditCard creditCard1 = new CreditCard(new Money(new BigDecimal(30000)), accountHolder1, admin, ACTIVE);

        String body = objectMapper.writeValueAsString(creditCard1);

        mockMvc.perform(post("/api/admins/accounts/credit-card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/api/admins/accounts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Rebecca T. Carroll"));

        mockMvc.perform(delete("/api/admins/accounts/11"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void saveThirdPartyUser_validThirdPartyUser_thirdPartyUserSaved() throws Exception {
        Admin admin = new Admin("George L. Lucas");
        adminRepository.save(admin);
        ThirdPartyUser thirdParty1 = new ThirdPartyUser("GF8754E", "Diana J. Cano", admin);

        String body = objectMapper.writeValueAsString(thirdParty1);

        mockMvc.perform(post("/api/admins/third-party-users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/api/admins/third-party-users"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Diana J. Cano"));

        mockMvc.perform(delete("/api/admins/third-party-users/GF8754E"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateAccountBalance_validBalance_balanceUpdated() throws Exception {
        AccountBalanceDto accountBalanceDto = new AccountBalanceDto(new Money(new BigDecimal(888888)));
        String body = objectMapper.writeValueAsString(accountBalanceDto);

        mockMvc.perform(patch("/api/admins/accounts/1/balance").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/api/admins/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        assertTrue(mvcResult.getResponse().getContentAsString().contains("888888"));
    }

    @Test
    void deleteAccount_accountDeleted() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Admin admin = new Admin("George L. Lucas");
        adminRepository.save(admin);
        Address address1 = new Address("James Street", "739");
        addressRepository.save(address1);
        LocalDate date1 = LocalDate.of(1946, 2, 18);
        AccountHolder accountHolder1 = new AccountHolder("Rebecca T. Carroll", date1, address1);
        accountHolderRepository.save(accountHolder1);
        CreditCard creditCard1 = new CreditCard(new Money(new BigDecimal(30000)), accountHolder1, admin, ACTIVE);

        String body = objectMapper.writeValueAsString(creditCard1);

        mockMvc.perform(post("/api/admins/accounts/credit-card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(delete("/api/admins/accounts/11"))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/api/admins/accounts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertFalse(mvcResult.getResponse().getContentAsString().contains("Rebecca T. Carroll"));

    }

    @Test
    void deleteThirdPartyUser() throws Exception {
        Admin admin = new Admin("George L. Lucas");
        adminRepository.save(admin);
        ThirdPartyUser thirdParty1 = new ThirdPartyUser("GF8754E", "Diana J. Cano", admin);

        String body = objectMapper.writeValueAsString(thirdParty1);

        mockMvc.perform(post("/api/admins/third-party-users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(delete("/api/admins/third-party-users/GF8754E"))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/api/admins/third-party-users"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        assertFalse(mvcResult.getResponse().getContentAsString().contains("Diana J. Cano"));
    }
}