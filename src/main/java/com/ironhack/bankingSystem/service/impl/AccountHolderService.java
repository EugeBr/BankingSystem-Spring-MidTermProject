package com.ironhack.bankingSystem.service.impl;

import com.ironhack.bankingSystem.model.*;
import com.ironhack.bankingSystem.repository.*;
import com.ironhack.bankingSystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderService implements IAccountHolderService {

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
    public List<Account> getAllAccountHoldersAccounts(Integer id) {
        List<Account> accountList = new ArrayList<>();

        List<Checking> primaryCheckingList = checkingRepository.findAllByPrimaryOwnerIdParam(id);
        if(primaryCheckingList.size() != 0) { accountList.addAll(primaryCheckingList); }
        List<Checking> secondaryCheckingList = checkingRepository.findAllBySecondaryOwnerIdParam(id);
        if(secondaryCheckingList.size() != 0) { accountList.addAll(secondaryCheckingList); }
        List<StudentChecking> primaryStudentCheckingList = studentCheckingRepository.findAllByPrimaryOwnerIdParam(id);
        if(primaryStudentCheckingList.size() != 0) { accountList.addAll(primaryStudentCheckingList); }
        List<StudentChecking> secondaryStudentCheckingList = studentCheckingRepository.findAllBySecondaryOwnerIdParam(id);
        if(secondaryStudentCheckingList.size() != 0) { accountList.addAll(secondaryStudentCheckingList); }
        List<Savings> primarySavingsList = savingsRepository.findAllByPrimaryOwnerIdParam(id);
        if(primarySavingsList.size() != 0) { accountList.addAll(primarySavingsList); }
        List<Savings> secondarySavingsList = savingsRepository.findAllBySecondaryOwnerIdParam(id);
        if(secondarySavingsList.size() != 0) { accountList.addAll(secondarySavingsList); }
        List<CreditCard> primaryCreditCardList = creditCardRepository.findAllByPrimaryOwnerIdParam(id);
        if(primaryCreditCardList.size() != 0) { accountList.addAll(primaryCreditCardList); }
        List<CreditCard> secondaryCreditCardList = creditCardRepository.findAllBySecondaryOwnerIdParam(id);
        if(secondaryCreditCardList.size() != 0) { accountList.addAll(secondaryCreditCardList); }

        if(accountList.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has no accounts yet");
        return accountList;
    }

    @Override
    public Account getAccountById(Integer id, Integer accountId) {
        List<Account> accountList = getAllAccountHoldersAccounts(id);
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if(accountOptional.isEmpty() || !accountList.contains(accountOptional.get()) ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + accountId + " doesn't exist or belongs to another user");
        }
        return accountOptional.get();
    }


}
