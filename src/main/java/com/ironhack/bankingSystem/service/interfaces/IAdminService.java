package com.ironhack.bankingSystem.service.interfaces;

import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.model.Account;
import com.ironhack.bankingSystem.model.Checking;

public interface IAdminService {

    Account getAccountById(Integer id);
    ResponseMessage saveCheckingAccount(Checking checking);

}
