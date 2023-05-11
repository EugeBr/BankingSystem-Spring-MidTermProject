package com.ironhack.bankingSystem.controller.interfaces;

import com.ironhack.bankingSystem.classes.ThirdPartyTransferRequest;
import com.ironhack.bankingSystem.classes.TransferRequest;
import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.model.Account;

import java.security.Principal;
import java.util.List;

public interface IAccountHolderController {

    List<Account> getAllAccountHoldersAccounts(Integer id, Principal principal);
    Account getAccountById(Integer id, Integer accountId, Principal principal);
    ResponseMessage transferFunds(Integer id, TransferRequest transferRequest, Principal principal);
    ResponseMessage transferFundsToThirdParty(Integer id, String hashedKey, ThirdPartyTransferRequest thirdPartyTransferRequest, Principal principal);
}
