package com.ironhack.bankingSystem.controller.interfaces;

import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.classes.ThirdPartyTransferRequest;

public interface IThirdPartyUserController {

    ResponseMessage transferFundsFromThirdParty(String hashedKey, ThirdPartyTransferRequest thirdPartyTransferRequest);
}
