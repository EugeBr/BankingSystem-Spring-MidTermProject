package com.ironhack.bankingSystem.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.bankingSystem.classes.ThirdPartyTransferRequest;
import com.ironhack.bankingSystem.classes.TransferRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountHolderControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllAccountHoldersAccounts_validId_correctAccountList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/account-holders/4"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        assertTrue(mvcResult.getResponse().getContentAsString().contains("SAVINGS ACCOUNT"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("CREDIT CARD"));
    }

    @Test
    void getAllAccountHoldersAccounts_invalidId_NotFound() throws Exception {
        mockMvc.perform(get("/api/account-holders/44").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void getAccountById_validId_correctAccount() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/account-holders/4").param("accountId", "6"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        assertTrue(mvcResult.getResponse().getContentAsString().contains("SAVINGS ACCOUNT"));
    }

    @Test
    void getAccountById_invalidId_NotFound() throws Exception {
        mockMvc.perform(get("/api/account-holders/44").param("accountId", "66").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testTransferFunds_validRequest_fundsTransferred() throws Exception {
        TransferRequest transferRequest = new TransferRequest(5, 8,  new BigDecimal(10));
        String body = objectMapper.writeValueAsString(transferRequest);
        MvcResult mvcResult = mockMvc.perform(post("/api/account-holders/2/transfer")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testTransferFundsToThirdParty_validRequest_fundsTransferred() throws Exception {
        ThirdPartyTransferRequest transferRequest = new ThirdPartyTransferRequest(new BigDecimal(100), 8, "FSJ665A");
        String body = objectMapper.writeValueAsString(transferRequest);
        MvcResult mvcResult = mockMvc.perform(post("/api/account-holders/2/transfer-to-third-party/HG6588E")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}