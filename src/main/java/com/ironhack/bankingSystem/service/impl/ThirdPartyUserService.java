package com.ironhack.bankingSystem.service.impl;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.classes.ThirdPartyTransferRequest;
import com.ironhack.bankingSystem.model.Account;
import com.ironhack.bankingSystem.model.ThirdPartyUser;
import com.ironhack.bankingSystem.repository.*;
import com.ironhack.bankingSystem.service.interfaces.IThirdPartyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.ironhack.bankingSystem.model.enums.Status.FROZEN;

@Service
public class ThirdPartyUserService implements IThirdPartyUserService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ThirdPartyUserRepository thirdPartyUserRepository;

    @Override
    public ResponseMessage transferFundsFromThirdParty(String hashedKey, ThirdPartyTransferRequest thirdPartyTransferRequest) {
        Optional<Account> recipientsAccount = accountRepository.findById(thirdPartyTransferRequest.getAccountId());
        Optional<ThirdPartyUser> sendersAccount = thirdPartyUserRepository.findById(hashedKey);
        if (sendersAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Third party user " + hashedKey + " not found");
        }else if (recipientsAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account " + thirdPartyTransferRequest.getAccountId() + " not found");
        }else if(recipientsAccount.get().getStatus() == FROZEN){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Recipient account " + thirdPartyTransferRequest.getAccountId() + " is FROZEN");
        }
        Money newRecipientBalance = new Money(recipientsAccount.get().getBalance().increaseAmount(thirdPartyTransferRequest.getAmount()));
        recipientsAccount.get().setBalance(newRecipientBalance);
        accountRepository.save(recipientsAccount.get());

        return new ResponseMessage("Funds transferred successfully");
    }
}

