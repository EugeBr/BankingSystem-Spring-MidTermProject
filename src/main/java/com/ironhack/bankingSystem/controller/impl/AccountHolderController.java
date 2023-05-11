package com.ironhack.bankingSystem.controller.impl;

import com.ironhack.bankingSystem.classes.ThirdPartyTransferRequest;
import com.ironhack.bankingSystem.classes.TransferRequest;
import com.ironhack.bankingSystem.classes.ResponseMessage;
import com.ironhack.bankingSystem.controller.interfaces.IAccountHolderController;
import com.ironhack.bankingSystem.model.Account;
import com.ironhack.bankingSystem.service.interfaces.IAccountHolderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/account-holders")
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    IAccountHolderService accountHolderService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccountHoldersAccounts(@PathVariable Integer id, Principal principal) {
        String userName = principal.getName();
        return accountHolderService.getAllAccountHoldersAccounts(id, userName);
    }

    @GetMapping("/{id}/accountId")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable Integer id, @RequestParam Integer accountId, Principal principal) {
        String userName = principal.getName();
        return accountHolderService.getAccountById(id, accountId, userName);
    }

    // replaced status.NO_CONTENT with status.OK, so I can return a message
    @PostMapping("/{id}/transfer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage transferFunds(
            @PathVariable Integer id,
            @RequestBody @Valid TransferRequest transferRequest,
            Principal principal
    ) {
        String userName = principal.getName();
        return accountHolderService.transferFunds(id, transferRequest, userName);
    }

    @PostMapping("/{id}/transfer-to-third-party/{hashedKey}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage transferFundsToThirdParty(
            @PathVariable Integer id,
            @PathVariable String hashedKey,
            @RequestBody @Valid ThirdPartyTransferRequest thirdPartyTransferRequest,
            Principal principal
    ) {
        String userName = principal.getName();
        return accountHolderService.transferFundsToThirdParty(id, hashedKey, thirdPartyTransferRequest, userName);
    }

}
