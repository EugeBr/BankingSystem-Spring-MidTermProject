package com.ironhack.bankingSystem.controller.impl;

import com.ironhack.bankingSystem.classes.TransferRequest;
import com.ironhack.bankingSystem.classes.TransferResponse;
import com.ironhack.bankingSystem.controller.interfaces.IAccountHolderController;
import com.ironhack.bankingSystem.model.Account;
import com.ironhack.bankingSystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-holders")
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    IAccountHolderService accountHolderService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccountHoldersAccounts(@PathVariable Integer id) {
        return accountHolderService.getAllAccountHoldersAccounts(id);
    }

    @GetMapping("/{id}/accountId")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable Integer id, @RequestParam Integer accountId) {
        return accountHolderService.getAccountById(id, accountId);
    }

    @PostMapping("/{id}/transfer")
    @ResponseStatus(HttpStatus.OK)
    public TransferResponse transferFunds(
            @PathVariable Integer id,
            @RequestBody TransferRequest transferRequest
    ) {
        return accountHolderService.transferFunds(id, transferRequest);
    }

}
