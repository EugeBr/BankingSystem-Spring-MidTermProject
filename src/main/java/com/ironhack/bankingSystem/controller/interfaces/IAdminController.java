package com.ironhack.bankingSystem.controller.interfaces;

import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.controller.dto.AccountBalanceDto;
import com.ironhack.bankingSystem.model.Account;
import com.ironhack.bankingSystem.model.Checking;
import com.ironhack.bankingSystem.model.CreditCard;
import com.ironhack.bankingSystem.model.Savings;
import java.util.List;

public interface IAdminController {

    List<Account> getAllAccounts();
    Account getAccountById(Integer id);
    ResponseMessage saveCheckingAccount(Checking checking);
    ResponseMessage saveSavingsAccount(Savings savings);
    ResponseMessage saveCreditCardAccount(CreditCard creditCard);
    ResponseMessage updateAccountBalance(Integer id, AccountBalanceDto accountBalanceDto);
}
