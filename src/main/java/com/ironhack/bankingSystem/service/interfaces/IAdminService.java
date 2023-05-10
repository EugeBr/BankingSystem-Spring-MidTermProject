package com.ironhack.bankingSystem.service.interfaces;

import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.model.Account;
import com.ironhack.bankingSystem.model.Checking;
import com.ironhack.bankingSystem.model.CreditCard;
import com.ironhack.bankingSystem.model.Savings;

public interface IAdminService {

    Account getAccountById(Integer id);
    ResponseMessage saveCheckingAccount(Checking checking);
    ResponseMessage saveSavingsAccount(Savings savings);
    ResponseMessage saveCreditCardAccount(CreditCard creditCard);
}
