package com.ironhack.bankingSystem.service.impl;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.classes.ThirdPartyTransferRequest;
import com.ironhack.bankingSystem.classes.TransferRequest;
import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.model.*;
import com.ironhack.bankingSystem.model.enums.Status;
import com.ironhack.bankingSystem.repository.*;
import com.ironhack.bankingSystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ironhack.bankingSystem.model.enums.Status.FROZEN;

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

    @Autowired
    ThirdPartyUserRepository thirdPartyUserRepository;

    @Override
    public List<Account> getAllAccountHoldersAccounts(Integer id) {
        List<Account> accountList = new ArrayList<>();

        List<Checking> primaryCheckingList = checkingRepository.findAllByPrimaryOwnerIdParam(id);
        if (primaryCheckingList.size() != 0) {
            accountList.addAll(primaryCheckingList);
        }
        List<Checking> secondaryCheckingList = checkingRepository.findAllBySecondaryOwnerIdParam(id);
        if (secondaryCheckingList.size() != 0) {
            accountList.addAll(secondaryCheckingList);
        }
        List<StudentChecking> primaryStudentCheckingList = studentCheckingRepository.findAllByPrimaryOwnerIdParam(id);
        if (primaryStudentCheckingList.size() != 0) {
            accountList.addAll(primaryStudentCheckingList);
        }
        List<StudentChecking> secondaryStudentCheckingList = studentCheckingRepository.findAllBySecondaryOwnerIdParam(id);
        if (secondaryStudentCheckingList.size() != 0) {
            accountList.addAll(secondaryStudentCheckingList);
        }
        List<Savings> primarySavingsList = savingsRepository.findAllByPrimaryOwnerIdParam(id);
        if (primarySavingsList.size() != 0) {
            accountList.addAll(primarySavingsList);
        }
        List<Savings> secondarySavingsList = savingsRepository.findAllBySecondaryOwnerIdParam(id);
        if (secondarySavingsList.size() != 0) {
            accountList.addAll(secondarySavingsList);
        }
        List<CreditCard> primaryCreditCardList = creditCardRepository.findAllByPrimaryOwnerIdParam(id);
        if (primaryCreditCardList.size() != 0) {
            accountList.addAll(primaryCreditCardList);
        }
        List<CreditCard> secondaryCreditCardList = creditCardRepository.findAllBySecondaryOwnerIdParam(id);
        if (secondaryCreditCardList.size() != 0) {
            accountList.addAll(secondaryCreditCardList);
        }

        if (accountList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has no accounts yet");
        return accountList;
    }

    @Override
    public Account getAccountById(Integer id, Integer accountId) {
        List<Account> accountList = getAllAccountHoldersAccounts(id);
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty() || !accountList.contains(accountOptional.get())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + accountId + " doesn't exist or belongs to another user");
        }
        accountOptional.get().checkMinimumBalance();
        accountOptional.get().checkMonthlyFee();
        accountOptional.get().checkInterest();
        return accountOptional.get();
    }

    @Override
    public ResponseMessage transferFunds(Integer id, TransferRequest transferRequest) {
        List<Account> accountList = getAllAccountHoldersAccounts(id);
        Optional<Account> sendersAccount = accountRepository.findById(transferRequest.getAccountId());
        Optional<Account> recipientsAccount = accountRepository.findById(transferRequest.getRecipientAccountId());
        if (sendersAccount.isEmpty() || !accountList.contains(sendersAccount.get())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + transferRequest.getAccountId() + " doesn't exist or belongs to another user");
        }else if (recipientsAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient account " + transferRequest.getRecipientAccountId() + " not found");
        }else if(sendersAccount.get().getStatus() == FROZEN){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Account " + transferRequest.getAccountId() + " is FROZEN");
        }else if(recipientsAccount.get().getStatus() == FROZEN){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Recipient account " + transferRequest.getRecipientAccountId() + " is FROZEN");
        }else if (sendersAccount.get().getBalance().getAmount().compareTo(transferRequest.getAmount()) < 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Account " + transferRequest.getAccountId() + " doesn't have sufficient funds for this transaction");
        }
        Money newSendersBalance = new Money(sendersAccount.get().getBalance().decreaseAmount(transferRequest.getAmount()));
        sendersAccount.get().setBalance(newSendersBalance);
        accountRepository.save(sendersAccount.get());
        sendersAccount.get().checkMinimumBalance();

        Money newRecipientBalance = new Money(recipientsAccount.get().getBalance().increaseAmount(transferRequest.getAmount()));
        recipientsAccount.get().setBalance(newRecipientBalance);
        accountRepository.save(recipientsAccount.get());

        return new ResponseMessage("Funds transferred successfully");
    }

    @Override
    public ResponseMessage transferFundsToThirdParty(Integer id, String hashedKey, ThirdPartyTransferRequest thirdPartyTransferRequest) {
        List<Account> accountList = getAllAccountHoldersAccounts(id);
        Optional<Account> sendersAccount = accountRepository.findById(thirdPartyTransferRequest.getAccountId());
        Optional<ThirdPartyUser> recipientsAccount = thirdPartyUserRepository.findById(hashedKey);
        if (sendersAccount.isEmpty() || !accountList.contains(sendersAccount.get())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + thirdPartyTransferRequest.getAccountId() + " doesn't exist or belongs to another user");
        }else if(sendersAccount.get().getStatus() == FROZEN){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Account " + thirdPartyTransferRequest.getAccountId() + " is FROZEN");
        }else if (sendersAccount.get().getBalance().getAmount().compareTo(thirdPartyTransferRequest.getAmount()) < 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Account " + thirdPartyTransferRequest.getAccountId() + " doesn't have sufficient funds for this transaction");
        }else if (recipientsAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Third party user " + hashedKey + " not found");
        }
        Money newSendersBalance = new Money(sendersAccount.get().getBalance().decreaseAmount(thirdPartyTransferRequest.getAmount()));
        sendersAccount.get().setBalance(newSendersBalance);
        accountRepository.save(sendersAccount.get());
        sendersAccount.get().checkMinimumBalance();

        return new ResponseMessage("Funds transferred successfully");
    }
}
