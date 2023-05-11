package com.ironhack.bankingSystem.service.interfaces;

import com.ironhack.bankingSystem.classes.ThirdPartyTransferRequest;
import com.ironhack.bankingSystem.classes.TransferRequest;
import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.model.Account;

import java.util.List;

public interface IAccountHolderService {

    List<Account> getAllAccountHoldersAccounts(Integer id, String userName);
    Account getAccountById(Integer id, Integer accountId, String userName);
    ResponseMessage transferFunds(Integer id, TransferRequest transferRequest, String userName);
    ResponseMessage transferFundsToThirdParty(Integer id, String hashedKey, ThirdPartyTransferRequest thirdPartyTransferRequest, String userName);
}
