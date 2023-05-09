package com.ironhack.bankingSystem.service.interfaces;

import com.ironhack.bankingSystem.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountHolderService {

    List<Account> getAllAccountHoldersAccounts(Integer id);
    Account getAccountById(Integer id, Integer accountId);
}
