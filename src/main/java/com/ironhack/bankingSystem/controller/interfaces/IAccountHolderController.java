package com.ironhack.bankingSystem.controller.interfaces;

import com.ironhack.bankingSystem.classes.ThirdPartyTransferRequest;
import com.ironhack.bankingSystem.classes.TransferRequest;
import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.model.Account;

import java.util.List;

public interface IAccountHolderController {

    List<Account> getAllAccountHoldersAccounts(Integer id);
    Account getAccountById(Integer id, Integer accountId);
    ResponseMessage transferFunds(Integer id, TransferRequest transferRequest);
    ResponseMessage transferFundsToThirdParty(Integer id, String hashedKey, ThirdPartyTransferRequest thirdPartyTransferRequest);
}
