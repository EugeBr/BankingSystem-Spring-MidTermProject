package com.ironhack.bankingSystem.controller.interfaces;

import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.controller.dto.AccountBalanceDto;
import com.ironhack.bankingSystem.model.*;

import java.util.List;

public interface IAdminController {

    List<Account> getAllAccounts();
    Account getAccountById(Integer id);
    List<ThirdPartyUser> getAllThirdPartyUsers();
    ResponseMessage saveCheckingAccount(Checking checking);
    ResponseMessage saveSavingsAccount(Savings savings);
    ResponseMessage saveCreditCardAccount(CreditCard creditCard);
    ResponseMessage saveThirdPartyUser(ThirdPartyUser thirdPartyUser);
    ResponseMessage updateAccountBalance(Integer id, AccountBalanceDto accountBalanceDto);

}
