package com.ironhack.bankingSystem.controller.interfaces;

import com.ironhack.bankingSystem.classes.TransferRequest;
import com.ironhack.bankingSystem.classes.TransferResponse;
import com.ironhack.bankingSystem.model.Account;

import java.util.List;

public interface IAccountHolderController {

    List<Account> getAllAccountHoldersAccounts(Integer id);
    Account getAccountById(Integer id, Integer accountId);
    TransferResponse transferFunds(Integer id, TransferRequest transferRequest);
}
