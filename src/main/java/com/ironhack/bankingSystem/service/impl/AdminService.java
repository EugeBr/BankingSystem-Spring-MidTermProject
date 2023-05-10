package com.ironhack.bankingSystem.service.impl;

import com.ironhack.bankingSystem.model.Account;
import com.ironhack.bankingSystem.repository.*;
import com.ironhack.bankingSystem.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Override
    public Account getAccountById(Integer id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + id + " not found");
        return accountOptional.get();
    }
}
