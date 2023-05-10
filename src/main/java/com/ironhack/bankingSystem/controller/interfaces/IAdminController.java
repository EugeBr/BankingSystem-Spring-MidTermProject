package com.ironhack.bankingSystem.controller.interfaces;

import com.ironhack.bankingSystem.model.Account;

import java.util.List;

public interface IAdminController {

    List<Account> getAllAccounts();
}
