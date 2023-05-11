package com.ironhack.bankingSystem.service.interfaces;

import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.classes.ThirdPartyTransferRequest;

public interface IThirdPartyUserService {

    ResponseMessage transferFundsFromThirdParty(String hashedKey, ThirdPartyTransferRequest thirdPartyTransferRequest);
}
