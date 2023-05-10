package com.ironhack.bankingSystem.service.interfaces;

import com.ironhack.bankingSystem.model.Account;

public interface IAdminService {
    Account getAccountById(Integer id);
}
