package com.ironhack.bankingSystem.service.impl;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.controller.dto.AccountBalanceDto;
import com.ironhack.bankingSystem.model.*;
import com.ironhack.bankingSystem.repository.*;
import com.ironhack.bankingSystem.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
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
        if (accountOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + id + " not found");
        accountOptional.get().checkMinimumBalance();
        accountOptional.get().checkMonthlyFee();
        accountOptional.get().checkInterest();
        return accountOptional.get();
    }

    @Override
    public ResponseMessage saveCheckingAccount(Checking checking) {
        Optional<Checking> checkingOptional = checkingRepository.findBySecretKey(checking.getSecretKey());
        Period period = Period.between(checking.getPrimaryOwner().getDateOfBirth(), LocalDate.now());
        if (checkingOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "This account already exist");
        } else if (period.getYears() < 24) {
            StudentChecking studentAccount = new StudentChecking(checking);
            studentCheckingRepository.save(studentAccount);
            return new ResponseMessage("Student Checking account successfully created");
        }
        checkingRepository.save(checking);
        return new ResponseMessage("Checking account successfully created");
    }

    @Override
    public ResponseMessage saveSavingsAccount(Savings savings) {
        Optional<Savings> savingsOptional = savingsRepository.findBySecretKey(savings.getSecretKey());
        if (savingsOptional.isPresent()) {throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "This account already exist");}
        savingsRepository.save(savings);
        return new ResponseMessage("Savings account successfully created");
    }

    // users can have as many credit cards as they want
    // not checking for existence because id is auto-generated
    @Override
    public ResponseMessage saveCreditCardAccount(CreditCard creditCard) {
        creditCardRepository.save(creditCard);
        return new ResponseMessage("Credit Card account successfully created");
    }

    @Override
    public ResponseMessage updateAccountBalance(Integer id, Money balance) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account" + id + "not found"); Account account = accountOptional.get();
        accountOptional.get().setBalance(balance);
        accountRepository.save(accountOptional.get());
       // accountOptional.get().checkMinimumBalance();
        return new ResponseMessage("Account " + id + " balance has been successfully updated");
    }
}
