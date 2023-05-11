package com.ironhack.bankingSystem.controller.impl;

import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.classes.ThirdPartyTransferRequest;
import com.ironhack.bankingSystem.controller.interfaces.IThirdPartyUserController;
import com.ironhack.bankingSystem.service.impl.ThirdPartyUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/third-party-users")
public class ThirdPartyUserController implements IThirdPartyUserController {

    @Autowired
    ThirdPartyUserService thirdPartyUserService;

    @PostMapping("/transfer/{hashedKey}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage transferFundsFromThirdParty(
            @PathVariable String hashedKey,
            @RequestBody @Valid ThirdPartyTransferRequest thirdPartyTransferRequest
    ) {
        return thirdPartyUserService.transferFundsFromThirdParty(hashedKey, thirdPartyTransferRequest);
    }
}
