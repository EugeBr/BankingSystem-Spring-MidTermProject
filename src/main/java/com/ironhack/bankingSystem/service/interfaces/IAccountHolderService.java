package com.ironhack.bankingSystem.service.interfaces;

import com.ironhack.bankingSystem.classes.TransferRequest;
import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.model.Account;

import java.util.List;

public interface IAccountHolderService {

    List<Account> getAllAccountHoldersAccounts(Integer id);
    Account getAccountById(Integer id, Integer accountId);
    ResponseMessage transferFunds(Integer id, TransferRequest transferRequest);
}