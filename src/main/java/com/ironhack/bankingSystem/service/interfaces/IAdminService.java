package com.ironhack.bankingSystem.service.interfaces;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.model.*;

public interface IAdminService {

    Account getAccountById(Integer id);
    ResponseMessage saveCheckingAccount(Checking checking);
    ResponseMessage saveSavingsAccount(Savings savings);
    ResponseMessage saveCreditCardAccount(CreditCard creditCard);
    ResponseMessage saveThirdPartyUser(ThirdPartyUser thirdPartyUser);
    ResponseMessage updateAccountBalance(Integer id, Money accountBalanceDto);
    ResponseMessage deleteAccount(Integer id);
    ResponseMessage deleteThirdPartyUser(String hashedKey);
}
